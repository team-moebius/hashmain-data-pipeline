import React from 'react';

class Button extends React.Component {
    static defaultProps = {
        classType: "",
        buttonType: "btn-st0",
        buttonName: "Input the Button Name"
    }
    render(){
        return  (
            <div className={`a-row ${this.props.classType}`}>
                <span class={`btn-base ${this.props.buttonType}`}>
                    <button>{this.props.buttonName}</button>
                </span>
            </div>
        );
    }
}

export default Button;