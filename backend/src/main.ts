import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { Logger, ValidationPipe } from '@nestjs/common';

const PORT = 3000;

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const logger = new Logger();

  app.useGlobalPipes(new ValidationPipe());
  app.useLogger(logger);

  await app.listen(PORT);
  logger.log(`Server running on port ${PORT}.`);
}
bootstrap();
