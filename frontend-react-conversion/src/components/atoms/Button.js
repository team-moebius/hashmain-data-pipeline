import React from 'react';

class InputBox extends React.Component {
    static defaultProps = {
        buttonType: "btn-st0",
        buttonName: "Input the Button Name"
    }
    render(){
        return  (
            <span class={`btn-base ${this.props.buttonType}`}>
                <input type="text" class="input-base inp-st2" placeholder={this.props.buttonName}></input>
            </span>
        );
    }
}

export default InputBox;