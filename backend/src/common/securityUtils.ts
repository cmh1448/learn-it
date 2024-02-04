import { Logger } from '@nestjs/common';
const crypto = async () => await import('crypto');

export const createSalt = async () => (await crypto()).randomBytes(64).toString('base64');

export const createHashedPassword = async (password: string, salt?: string | undefined) => {
  if (!salt) salt = await createSalt();

  const hash = (await crypto()).pbkdf2Sync(password, salt, 1000, 64, 'sha512').toString('base64');
  return { salt, hash };
};
