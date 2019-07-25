const validateEmail = (email: string) => {
  const regex: RegExp = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
  return regex.test(email);
};

const validatePassword = (password: string) => {
  const regex: RegExp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,30}$/;
  return regex.test(password);
};

const validatePhoneNumber = (phoneNumber: string) => {
  const regex: RegExp = /^\d{3}\d{3,4}\d{4}$/;
  return regex.test(phoneNumber);
};

const isExistInput = (input: string) => {
  return input && input.length > 0;
};

export default { validateEmail, validatePassword, validatePhoneNumber, isExistInput };
