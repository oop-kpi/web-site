import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import React, { Component } from 'react';
import axios from "axios";
import {API_URL} from './constants/ApiConstants'
import { createMuiTheme } from '@material-ui/core/styles';
import blue from '@material-ui/core/colors/blue';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import {Input} from "@material-ui/core";


const theme = createMuiTheme({
    palette: {
        primary: blue,
    },
});
const useStyles = makeStyles({
    root: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        border: 0,
        borderRadius: 3,
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        color: 'white',
        height: 48,
        padding: '0 30px',
    },
});
class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: 'ff',
            password: '',
        };
    }

    myChangeHandler = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
    }

    handleClick(event) {
        var apiBaseUrl = API_URL;
        var self = this;
        alert(this.state.login)
        var payload = {
            "login": this.state.login,
            "password": this.state.password
        }
        axios.post(apiBaseUrl + 'auth/login', payload)
            .then(function (response) {
                console.log(response);
                if (response.data.code == 200) {
                    console.log("Login successful");
                    var uploadScreen = [];
                    self.props.appContext.setState({loginPage: [], uploadScreen: uploadScreen})
                } else if (response.data.code == 204) {
                    console.log("Username password do not match");
                    alert("username password do not match")
                } else {
                    console.log("Username does not exists");
                    alert("Username does not exist");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <div>

                        <Typography component="h1" variant="h5">
                            Sign in
                        </Typography>
                        <h1> {this.state.login}</h1>
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
                        <Button
                            className={useStyles}
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            onClick={event => this.handleClick(event)}
                        >
                            Sign In
                        </Button>
                        <Grid container>
                            <Grid item xs>
                                <Link href="#" variant="body2">
                                    Forgot password?
                                </Link>
                            </Grid>
                            <Grid item>
                                <Link href="#" variant="body2">
                                    {"Don't have an account? Sign Up"}
                                </Link>
                            </Grid>
                        </Grid>
                  
                </div>
            </Container>
        );
    }
}
export default Login;