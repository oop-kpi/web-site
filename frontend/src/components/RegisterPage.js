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
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';


const MyButton = styled(Button)({
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
});
class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            password: '',
            fio: '',
            group: '',
            email:'',
            errorMsg:''
        };
    }
    setAge = (event) =>{
        this.setState({"group":event.target.value})
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
            "email": this.state.email,
            "group": this.state.group,
            "name": this.state.fio,
        }
        axios.post(apiBaseUrl + 'auth/signup', payload)
            .then(function (response) {
                    console.log("Login successful");
                }
            ).catch(req => (req))

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
                        Реєстрація
                    </Typography>
                    <form onSubmit={event => this.handleClick(event)}>
                        <TextField
                            ref="login"
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="login"
                            label="Логін"
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
                            label="Пароль"
                            type="password"
                            id="password"
                            onChange={this.myChangeHandler}
                        />
                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="text"
                            fullWidth
                            name="fio"
                            label="ПІБ"
                            id="password"
                            onChange={this.myChangeHandler}
                        />
                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="text"
                            fullWidth
                            name="email"
                            label="Пошта"
                            id="password"
                            onChange={this.myChangeHandler}
                        />
                        <InputLabel id="demo-simple-select-label">Група:</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="group"
                            value={this.state.group}
                            onChange={event => this.setAge(event)}
                        >
                            <MenuItem value={"IO93"}>IO93</MenuItem>
                            <MenuItem value={"IO92"}>IO92</MenuItem>
                            <MenuItem value={"IO91"}>IO91</MenuItem>
                            <MenuItem value={"IB93"}>IB93</MenuItem>
                            <MenuItem value={"IB92"}>IB92</MenuItem>
                            <MenuItem value={"IB91"}>IB91</MenuItem>
                        </Select>

                        <MyButton
                            type="submit"
                            fullWidth
                            variant="contained"
                        >
                            Зареєструватись
                        </MyButton>
                        {this.state.errorMsg ?
                            <Alert severity="error">
                                <AlertTitle>{this.state.errorMsg}</AlertTitle>
                            </Alert> : <dir></dir>
                        }
                    </form>
                    <Grid container>
                        <Grid item>
                            <Link href="/login" variant="body2">
                                {"Маєте аккаунт? Увійти"}
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
export default RegisterPage;