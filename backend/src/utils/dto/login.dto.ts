import { IsEmail, IsNotEmpty } from 'class-validator';

class RegisterDTO {
  @IsEmail()
  email: string;

  @IsNotEmpty()
  password: string;
}

export default RegisterDTO;
