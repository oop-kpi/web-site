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
import {Redirect} from "react-router";


const MyButton = styled(Button)({
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
});
class UploadLabComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            link: '',
            file: null,
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
        var formData = new FormData()
        formData.append("name",this.state.name)
        if (this.state.link === ''&& this.state.file==null){
            alert("Оберіть файл або посилання!")
        }
        if (this.state.file !=null)
        formData.append("presentation",this.state.file)
        if (this.state.link !=='')
            formData.append("link",this.state.link)
        axios.post(apiBaseUrl + 'lab/create', formData,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}})
            .then(function (response) {
                    alert("Успішно!")
                }
            ).catch(req => this.setState({"errorMsg":req.toString()}))

    }

    render() {
        if (localStorage.getItem('user')==null){
            return <Redirect to={"/login?err=401"}></Redirect>
        }
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
                        Завантажити лабораторну роботу
                    </Typography>
                    <form onSubmit={event => this.handleClick(event)} noValidate>
                        <TextField
                            ref="login"
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="login"
                            label="Номер лабораторної"
                            name="name"
                            autoComplete="email"
                            placeholder="Лабораторна робота №1"
                            autoFocus
                            onChange={this.myChangeHandler}
                        />

                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="text"
                            fullWidth
                            name="link"
                            label="Посилання на диск/гітхаб/ тощо, де знаходиться робота"
                            id="password"
                            onChange={this.myChangeHandler}
                        />
                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="file"
                            fullWidth
                            name="file"
                            label="Архів з лабою"
                            id="password"
                            onChange={this.myChangeHandler}
                        />

                        <MyButton
                            type="submit"
                            fullWidth
                            variant="contained"
                        >
                            Завантажити
                        </MyButton>
                        {this.state.errorMsg ?
                            <Alert severity="error">
                                <AlertTitle>{this.state.errorMsg}</AlertTitle>
                            </Alert> : <dir></dir>
                        }
                    </form>
                    <Grid/>
                    <Grid/>
                    <Grid/>  </Grid>

            </Grid>
        );
    }
}
export default UploadLabComponent;