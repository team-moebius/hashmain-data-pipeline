import React from 'react';
import classNames from 'classnames';

interface InputBoxProps {
  className?: string;
  inputStyle?: string;
  inputType?: string;
  placeholder: string;
  isPhone?: boolean;
  isAlert?: boolean;
  value: string;
  changeHandler(event: any): void;
}

const InputBox: React.FC<InputBoxProps> = ({className, inputStyle, inputType, placeholder, isPhone, isAlert, value, changeHandler}: InputBoxProps) => (
  <div className={classNames(
    'a-row',
    className,
    {'isalert': isAlert},
  )}>
    <input
      type={inputType}
      className={classNames(
        'input-base',
        'inp-st' + inputStyle,
        {'u-phone': isPhone}
      )}
      value={value}
      placeholder={placeholder}
      onChange={(event) => changeHandler(event)}
    />
  </div>
);

InputBox.defaultProps = {
  isAlert: false,
  className: '',
  inputType: 'text',
  inputStyle: '2',
  placeholder: 'Input the info',
  isPhone: false,
};

export default InputBox;