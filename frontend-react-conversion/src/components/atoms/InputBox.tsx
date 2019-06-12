import React from 'react';
import classNames from 'classnames';

interface Props {
  className?: string,
  inputStyle?: string,
  inputType?: string,
  placeholder: string,
  isPhone?: boolean,
  isAlert?: boolean,
  value: string,
  changeHandler: any,
}

class InputBox extends React.Component<Props> {
  static defaultProps = {
    isAlert: false,
    className: '',
    inputType: 'text',
    inputStyle: '2',
    placeholder: 'Input the info',
    isPhone: false,
  };

  render(){
    return  (
      <div className={classNames(
        'a-row',
        this.props.className,
        {'isalert' : this.props.isAlert},
      )}>
        <input
          type={this.props.inputType}
          className={classNames(
          'input-base',
          'inp-st' + this.props.inputStyle,
          {'u-phone': this.props.isPhone}
          )}
          value={this.props.value}
          placeholder={this.props.placeholder}
          onChange={this.props.changeHandler}
        />
      </div>
    );
  }
}

export default InputBox;