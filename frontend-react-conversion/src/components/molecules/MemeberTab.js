import React from 'react';

class MemberTab extends React.Component {
    static defaultProps = {
        // 0 to login, 1 to join
        clicked : 0,
        href_list : {"#mb-login" : "로그인", "#mb-join" : "회원가입"}
    }
    mbtab() {
        var lis = []
        for (var key in this.props.href_list) {
            var isOn  = "";
            if (this.props.href_list[this.props.clicked] === key) {
                isOn = "on"
            }
            lis.push(<li className={isOn}><a href={key} onClick={this.handleClick(key).bind(this)}><span>{this.props.href_list[key]}</span></a></li>)
        }
        return (
            <ul className="ui-tab-menu mb-tab">
                {lis}
            </ul>
        );
    }
    render(){
        return  (
            <div className="a-row">
                <input type="text" className={`input-base ${this.props.inputType}`} placeholder={this.props.placeholder}></input>
            </div>
        );
    }
}

export default MemberTab;