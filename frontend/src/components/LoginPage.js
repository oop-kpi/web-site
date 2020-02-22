import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import React, { Component } from 'react';
import axios from "axios";
import {API_URL} from '../constants/ApiConstants'
import CssBaseline from '@material-ui/core/CssBaseline';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { Alert, AlertTitle } from '@material-ui/lab';
import { styled } from '@material-ui/core/styles';
import { withRouter } from 'react-router-dom';

const MyButton = styled(Button)({
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
});
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}
const vars = {
 '401':"Будь ласка, увійдіть"
}


class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            password: '',
            errorMsg:vars[getParameterByName('err')]
        };
    }


    myChangeHandler = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
    }

    handleClick = (event) => {
        event.preventDefault()
        var apiBaseUrl = API_URL;
        var self = this;
        var payload = {
            "login": this.state.login,
            "password": this.state.password,
        }
        axios.post(apiBaseUrl + 'auth/login', payload)
            .then(function (response) {
                localStorage.setItem("token",response.data.toString())
                document.location.href = '/lectures'

            }).catch(req => this.setState({"errorMsg":'Неправильні дані!'}))
    }


    render() {

        return (

            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
                justify="center"
                style={{ minHeight: '60vh' }}
            >


                <Grid item xs={3}>

                <CssBaseline/>
                <CssBaseline/>

                        <Typography component="h1" variant="h5">
                            Увійти
                        </Typography>
                <form onSubmit={event => this.handleClick(event)}>
                        <TextField
                            ref="login"
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="login"
                            label="Login"
                            name="login"
                            autoComplete="email"
                            autoFocus
                            onChange={this.myChangeHandler}
                        />
                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="password"
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            onChange={this.myChangeHandler}
                        />
                        <MyButton
                            type="submit"
                            fullWidth
                            variant="contained"
                        >
                            Увійти
                        </MyButton>
                    {this.state.errorMsg ?
                        <Alert severity="error">
                            <AlertTitle>{this.state.errorMsg}</AlertTitle>
                        </Alert> : <dir></dir>
                    }
                </form>
                        <Grid container>
                            <Grid item>
                                <Link href="/register" variant="body2">
                                    {"Зареєструватись"}
                                </Link>
                            </Grid>
                        </Grid>
                    <Grid/>
                    <Grid/>
                    <Grid/>  </Grid>

            </Grid>
        );
    }
}
export default withRouter(Login);