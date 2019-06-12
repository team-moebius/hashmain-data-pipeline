import React from 'react';


interface Props {
  children: string,
  buttonStyle?: string,
}

class Button extends React.Component<Props> {
  public static defaultProps = {
    buttonStyle: '2',
    children: 'Input button name as children',
  }

  render(){
    return  (
      <div className={`a-row btn-st${this.props.buttonStyle} btn-base`}>
        <button>{this.props.children}</button>
      </div>
    );
  }
}

export default Button;