import React from 'react';
// import {isTSUndefinedKeyword} from "@babel/types";

class InputBox extends React.Component {

    static defaultProps = {
        classType: "",
        inputType: "text",
        inputClassType: "inp-st0",
        placeholder: "Input the info",
        isPhone: false,
        value: "",
        changeHandler: ()=>{return undefined}
    }






    render(){
        return  (
            <div className={`a-row ${this.props.classType}`}>
                <input
                    type={this.props.inputType}
                    className={`input-base ${this.props.inputClassType} ${this.props.isPhone ? "u-phone" : ""}`}
                    value={this.props.value}
                    placeholder={this.props.placeholder}
                    onChange={this.props.changeHandler}
                ></input>
            </div>
        );
    }
}

export default InputBox;