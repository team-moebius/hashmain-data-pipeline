import React from 'react';

class InputBox extends React.Component {
    constructor(props) {
        super(props);
        var va = "value";
        this.state = {
            [va]: ""
        }
    }

    static defaultProps = {
        classType: "",
        inputType: "text",
        inputClassType: "inp-st0",
        placeholder: "Input the info",
        isPhone: false
    }

    autoHypenPhone(str){
        str = str.replace(/[^0-9]/g, '');
        var tmp = '';
        if( str.length < 4){
            return str;
        }else if(str.length < 7){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3);
            return tmp;
        }else if(str.length < 11){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 3);
            tmp += '-';
            tmp += str.substr(6);
            return tmp;
        }else{
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 4);
            tmp += '-';
            tmp += str.substr(7);
            return tmp;
        }
        return str;
    }

    handleChange(someparam, event) {
        console.log(someparam);
        console.log(event.target.value);

        this.setState({
            value: event.target.value,
        });
    }

    render(){
        return  (
            <div className={`a-row ${this.props.classType}`}>
                <input
                    type={this.props.inputType}
                    className={`input-base ${this.props.inputClassType} ${this.props.isPhone ? "u-phone" : ""}`}
                    value={this.state.value}
                    placeholder={this.props.placeholder}
                    onChange={this.handleChange.bind(this, "1")}
                ></input>
            </div>
        );
    }
}

export default InputBox;