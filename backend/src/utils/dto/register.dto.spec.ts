import { validate } from 'class-validator';
import { plainToInstance } from 'class-transformer';
import RegisterDTO from './register.dto';
import { flatten } from '@nestjs/common';

describe('RegisterDTO', () => {
  const INVALID_EMAIL_ERROR = 'email must be an email';
  const SHORT_PASSWORD_ERROR =
    'password must be longer than or equal to 8 characters';
  const LONG_PASSWORD_ERROR =
    'password must be shorter than or equal to 32 characters';
  const EMPTY_USERNAME_ERROR = 'username should not be empty';

  it.each([
    '',
    'invalid-email',
    'invalid-email@',
    'invalid-email@domain',
    '@domain.com',
    'email@',
    'email@domain',
    'email@domain.',
  ])('should throw error for invalid email `%s`', async (email) => {
    const registerDTO = plainToInstance(RegisterDTO, {
      email,
    });

    const errors = await validate(registerDTO);
    expect(errors[0].constraints).toEqual({
      isEmail: INVALID_EMAIL_ERROR,
    });
  });

  it.each`
    password                                | error
    ${''}                                   | ${SHORT_PASSWORD_ERROR}
    ${'1234567'}                            | ${SHORT_PASSWORD_ERROR}
    ${'abcDEFghIJKlmnopqrstuvvwxzy1234567'} | ${LONG_PASSWORD_ERROR}
  `(
    'should throw error for invalid password `$password`',
    async ({ password, error }) => {
      const registerDTO = plainToInstance(RegisterDTO, {
        password,
      });

      const errors = await validate(registerDTO);
      expect(errors[1].constraints).toEqual({
        isLength: error,
      });
    }
  );

  it('should throw error for empty username', async () => {
    const registerDTO = plainToInstance(RegisterDTO, {
      username: '',
    });

    const errors = await validate(registerDTO);
    expect(errors[2].constraints).toEqual({
      isNotEmpty: EMPTY_USERNAME_ERROR,
    });
  });

  it('should combine all errors', async () => {
    const registerDTO = plainToInstance(RegisterDTO, {});
    const errors = await validate(registerDTO);
    const flatErrors = flatten(errors.map((e) => e.constraints));

    expect(flatErrors).toEqual([
      { isEmail: INVALID_EMAIL_ERROR },
      { isLength: SHORT_PASSWORD_ERROR },
      { isNotEmpty: EMPTY_USERNAME_ERROR },
    ]);
  });

  it('should not throw error for valid data', async () => {
    const registerDTO = plainToInstance(RegisterDTO, {
      email: 'valid@email.com',
      password: 'validPassword132',
      username: 'validUsername',
    });

    await expect(validate(registerDTO)).resolves.toEqual([]);
  });
});
