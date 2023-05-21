import { Injectable } from '@nestjs/common';
import { UsersService } from '../users/users.service';
import { JwtService, JwtSignOptions } from '@nestjs/jwt';
import { RegisterDTO, LoginDTO } from '../utils/dto';
import { User } from '@prisma/client';

const getJwtOptions = (
  expiresIn: string,
  type: 'access' | 'refresh'
): JwtSignOptions => ({
  secret:
    type === 'access'
      ? process.env.JWT_ACCESS_SECRET
      : process.env.JWT_REFRESH_SECRET,
  expiresIn,
});

@Injectable()
export class AuthService {
  constructor(
    private readonly usersService: UsersService,
    private readonly jwtService: JwtService
  ) {}

  async login(data: LoginDTO) {
    const user = await this.usersService.findByCredentials(data);
    return this.generateUserResponse(user);
  }

  async register(data: RegisterDTO) {
    const user = await this.usersService.create(data);
    return this.generateUserResponse(user);
  }

  async logout(userId: string) {
    await this.usersService.findById(userId);
    return await this.usersService.setRefreshToken(userId, null);
  }

  async refresh(userId: string) {
    const user = await this.usersService.findById(userId);
    const [accessToken, refreshToken] = await this.generateTokensForUser(user);
    return {
      accessToken,
      refreshToken,
    };
  }

  private async generateUserResponse(user: Partial<User>) {
    const [accessToken, refreshToken] = await this.generateTokensForUser(user);
    await this.usersService.setRefreshToken(user.id, refreshToken);
    return {
      user,
      accessToken,
      refreshToken,
    };
  }

  private async generateTokensForUser(user: Partial<User>) {
    const payload = {
      sub: user.id,
      email: user.email,
      username: user.username,
    };

    return Promise.all([
      this.jwtService.signAsync(payload, getJwtOptions('15m', 'access')),
      this.jwtService.signAsync(payload, getJwtOptions('30d', 'refresh')),
    ]);
  }
}
