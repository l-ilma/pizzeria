import { Body, Controller, Get, Post, Req, UseGuards } from '@nestjs/common';
import { AuthService } from './auth.service';
import { RegisterDTO, LoginDTO } from '../utils/dto';
import { AccessTokenGuard } from './guards/access-token.guard';
import { RefreshTokenGuard } from './guards/refresh-token.guard';
import { Request } from 'express';

interface RequestWithUser extends Request {
  user: {
    id: string;
  };
}

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('login')
  async login(@Body() data: LoginDTO) {
    return await this.authService.login(data);
  }

  @Post('register')
  async register(@Body() registerData: RegisterDTO) {
    return await this.authService.register(registerData);
  }

  @UseGuards(AccessTokenGuard)
  @Post('logout')
  async logout(@Req() req: RequestWithUser) {
    return await this.authService.logout(req.user.id);
  }

  @UseGuards(RefreshTokenGuard)
  @Post('refresh')
  async refresh(@Req() req: RequestWithUser) {
    return await this.authService.refresh(req.user.id);
  }

  @UseGuards(AccessTokenGuard)
  @Get('authenticate')
  async authenticate() {
    return;
  }
}
