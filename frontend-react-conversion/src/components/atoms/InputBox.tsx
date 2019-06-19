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

const defaultProps = {
  isAlert: false,
  className: '',
  inputType: 'text',
  inputStyle: '2',
  placeholder: 'Input the info',
  isPhone: false,
};

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

InputBox.defaultProps = defaultProps;

// class InputBox extends React.Component<InputBoxProps> {
//   static defaultProps = {
//     isAlert: false,
//     className: '',
//     inputType: 'text',
//     inputStyle: '2',
//     placeholder: 'Input the info',
//     isPhone: false,
//   };
//
//   shouldComponentUpdate(nextProps: Readonly<InputBoxProps>, nextState: Readonly<{}>, nextContext: any): boolean {
//     return !(nextProps.value === this.props.value);
//   }
//
//   render() {
//     return (
//       <div className={classNames(
//         'a-row',
//         this.props.className,
//         {'isalert': this.props.isAlert},
//       )}>
//         <input
//           type={this.props.inputType}
//           className={classNames(
//             'input-base',
//             'inp-st' + this.props.inputStyle,
//             {'u-phone': this.props.isPhone}
//           )}
//           value={this.props.value}
//           placeholder={this.props.placeholder}
//           onChange={this.props.changeHandler}
//         />
//       </div>
//     );
//   }
// }

export default InputBox;