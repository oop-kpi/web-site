import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import React, { Component } from 'react';
import axios from "axios";
import {API_URL} from './constants/ApiConstants'
class Login extends Component {
    constructor(props){
        super(props);
        this.state={
            username    :'',
            password:''
        }
    }
    render() {
        return (
            <div>
                <MuiThemeProvider>
                    <div align="center">
                        <TextField
                            hintText="Логін"
                            floatingLabelText="Логін"
                            onChange = {(event,newValue) => this.setState({username:newValue})}
                        />
                        <br/>
                        <TextField
                            type="password"
                            hintText="Пароль"
                            floatingLabelText="Пароль"
                            onChange = {(event,newValue) => this.setState({password:newValue})}
                        />
                        <br/>
                        <RaisedButton label="Увійти!" primary={true} style={style} onClick={(event) => this.handleClick(event)}/>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
    handleClick(event){
        var apiBaseUrl = API_URL;
        var self = this;
        var payload={
            "login":this.state.username,
            "password":this.state.password
        }
        axios.post(apiBaseUrl+'auth/login', payload)
            .then(function (response) {
                console.log(response);
                if(response.data.code == 200){
                    console.log("Login successful");
                    var uploadScreen=[];
                    self.props.appContext.setState({loginPage:[],uploadScreen:uploadScreen})
                }
                else if(response.data.code == 204){
                    console.log("Username password do not match");
                    alert("username password do not match")
                }
                else{
                    console.log("Username does not exists");
                    alert("Username does not exist");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}
const style = {
    margin: 15,
};
export default Login;