import { IsEmail, IsNotEmpty, Length } from 'class-validator';
import { MAX_PASSWORD_LENGTH, MIN_PASSWORD_LENGTH } from '../constants';

class RegisterDTO {
  @IsEmail()
  email: string;

  @Length(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)
  password: string;

  @IsNotEmpty()
  username: string;
}

export default RegisterDTO;
