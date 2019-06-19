import React from 'react';

interface ButtonProps {
  children: string,
  buttonStyle?: string,
}

const defaultProps = {
  buttonStyle: '2',
  children: 'Input button name as children',
}

const Button : React.FunctionComponent<ButtonProps> = ({children, buttonStyle}) => (
  <div className={`a-row btn-st${buttonStyle} btn-base`}>
    <button>{children}</button>
  </div>
);

Button.defaultProps = defaultProps;

export default Button;