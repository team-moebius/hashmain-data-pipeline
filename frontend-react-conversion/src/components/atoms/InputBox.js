import React from 'react';

class InputBox extends React.Component {
    static defaultProps = {
        inputType : "inp-st0",
        placeholder: "Input the info"
    }
    render(){
        return  (
            <div class="a-row">
                <input type="text" class={`input-base ${this.props.inputType}`} placeholder={this.props.placeholder}></input>
            </div>
        );
    }
}

export default InputBox;