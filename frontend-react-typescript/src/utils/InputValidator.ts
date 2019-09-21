type ValidationType = 'email' | 'password' | 'tel';

class InputValidator {
  private static VALIDATION_REGEX: { [key in ValidationType]: RegExp } = {
    email: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/,
    password: /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,30}$/,
    tel: /^\d{3}\d{3,4}\d{4}$/,
  };

  public static isValid = (type: ValidationType, value: string) => {
    return InputValidator.nonBlank(value) && InputValidator.VALIDATION_REGEX[type].test(value);
  };

  public static nonValid = (type: ValidationType, value: string) => {
    return InputValidator.isBlank(value) || !InputValidator.VALIDATION_REGEX[type].test(value);
  };

  public static isBlank = (value: string) => {
    return !value || value.length === 0;
  };

  public static nonBlank = (value: string) => {
    return value && value.length !== 0;
  };
}

export default InputValidator;
