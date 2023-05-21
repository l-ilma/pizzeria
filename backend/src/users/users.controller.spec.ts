import { Test } from '@nestjs/testing';
import { UsersController } from './users.controller';
import { UsersService } from './users.service';
import { RegisterDTO } from '../utils/dto/register.dto';

describe('UsersController', () => {
  let usersController: UsersController;
  let usersService: UsersService;

  const ANY_REGISTER_RESPONSE = { username: 'test' };

  beforeEach(async () => {
    const app = await Test.createTestingModule({
      controllers: [UsersController],
      providers: [UsersService],
    }).compile();

    usersController = app.get<UsersController>(UsersController);
    usersService = app.get<UsersService>(UsersService);
  });

  describe('/users', () => {
    it('POST should call UsersService register method and return its response', async () => {
      const serviceRegisterSpy = jest
        .spyOn(usersService, 'register')
        .mockResolvedValue(ANY_REGISTER_RESPONSE as any);
      const result = await usersController.register({} as RegisterDTO);

      expect(serviceRegisterSpy).toHaveBeenCalled();
      expect(result).toBe(ANY_REGISTER_RESPONSE);
    });
  });
});
