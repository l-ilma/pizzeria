import {
  BadRequestException,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import * as bcrypt from 'bcrypt';
import { LoginDTO, RegisterDTO } from '../utils/dto';
import { User } from '@prisma/client';

const SALT_ROUNDS = 10;

@Injectable()
export class UsersService {
  constructor(private readonly prismaService: PrismaService) {}

  async create({ username, email, password }: RegisterDTO) {
    const usersWithSameUniqueIds = await this.prismaService.user.findMany({
      where: {
        OR: [{ username }, { email }],
      },
    });

    if (usersWithSameUniqueIds.length) {
      throw new BadRequestException('Username or email already exists');
    }

    const user = await this.prismaService.user.create({
      data: {
        username,
        email,
        password: await this.hashPassword(password),
      },
    });

    return this.filterUser(user);
  }

  async findById(id: string) {
    const user = await this.prismaService.user.findUnique({
      where: { id },
    });

    if (!user) {
      throw new UnauthorizedException('Authentication failed');
    }

    return this.filterUser(user);
  }

  async setRefreshToken(userId: string, refreshToken: string) {
    return await this.prismaService.user.update({
      where: { id: userId },
      data: {
        refreshToken,
      },
    });
  }

  async findByCredentials({ email, password }: LoginDTO) {
    const foundUser = await this.prismaService.user.findUnique({
      where: { email },
    });

    if (!foundUser) {
      throw new UnauthorizedException('Authentication failed');
    }

    const isPasswordValid = await bcrypt.compare(password, foundUser.password);

    if (!isPasswordValid) {
      throw new UnauthorizedException('Authentication failed');
    }

    return this.filterUser(foundUser);
  }

  private async hashPassword(password: string) {
    return bcrypt.hash(password, await bcrypt.genSalt(SALT_ROUNDS));
  }

  private async filterUser(user: User) {
    const { password, refreshToken, ...filteredUser } = user;
    return filteredUser;
  }
}
