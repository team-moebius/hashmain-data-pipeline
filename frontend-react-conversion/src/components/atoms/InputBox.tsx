import React from 'react';
import classNames from 'classnames';

interface InputBoxProps {
  className?: string,
  inputStyle?: string,
  inputType?: string,
  placeholder: string,
  isPhone?: boolean,
  isAlert?: boolean,
  value: string,
  changeHandler: any,
}

const InputBox: React.FunctionComponent<InputBoxProps> =
  ({className, inputStyle, inputType, placeholder, isPhone, isAlert, value, changeHandler}) => (
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
        onChange={changeHandler}
      />
    </div>
)

InputBox.defaultProps = {
  isAlert: false,
  className: '',
  inputType: 'text',
  inputStyle: '2',
  placeholder: 'Input the info',
  isPhone: false,
};

export default InputBox;