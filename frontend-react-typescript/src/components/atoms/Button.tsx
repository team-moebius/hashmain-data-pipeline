import React from 'react';

interface ButtonProps {
  children: string;
  buttonStyle?: string;
}

const Button: React.FC<ButtonProps> = ({ children, buttonStyle }: ButtonProps) => (
  <div className={`a-row btn-st${buttonStyle} btn-base`}>
    <button>{children}</button>
  </div>
);

Button.defaultProps = {
  buttonStyle: '2',
  children: 'Input button name as children',
};

export default Button;
