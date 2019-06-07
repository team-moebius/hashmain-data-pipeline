import React from 'react';

class InputBox extends React.Component {
    static defaultProps = {
        classType: "",
        inputType: "text",
        inputClassType: "inp-st0",
        placeholder: "Input the info",
        isPhone: false
    }
    render(){
        return  (
            <div className={`a-row ${this.props.classType}`}>
                <input type={this.props.inputType} className={`input-base ${this.props.inputClassType} ${this.props.isPhone ? "u-phone" : ""}`} placeholder={this.props.placeholder}></input>
            </div>
        );
    }
}

export default InputBox;