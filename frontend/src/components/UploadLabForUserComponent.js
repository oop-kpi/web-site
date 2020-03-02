import React from "react";
import {API_URL} from "../constants/ApiConstants";
import axios from "axios";
import {Redirect, withRouter} from "react-router";
import Grid from "@material-ui/core/Grid";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import {LinearProgress} from "@material-ui/core";
import {Alert, AlertTitle} from "@material-ui/lab";
import Button from "@material-ui/core/Button";

class UploadLabForUserComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            link: '',
            file: null,
            mark: '',
            loading:false,
            currLogin:''
        };
    }
    componentDidMount() {
        const { login } = this.props.match.params;
        this.setState({currLogin:login})
    }
    setFile = (event) =>{
        this.setState({file:event.target.files[0]})
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
            formData.append("file",this.state.file)
        if (this.state.link !=='')
            formData.append("link",this.state.link)
        formData.append("mark",this.state.mark)
        this.setState({loading:true})
        axios.post(apiBaseUrl + 'lab/create/'+this.state.currLogin, formData,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}})
            .then(response =>
                this.setState({loading:false})

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
                        Завантажити лабораторну роботу для {this.state.currLogin}
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
                            placeholder="Лабораторна робота №"
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
                            onChange={this.setFile}
                        />
                        <TextField
                            ref="password"
                            variant="outlined"
                            margin="normal"
                            required
                            type="text"
                            fullWidth
                            name="mark"
                            label="Балл"
                            id="mark"
                            onChange={this.myChangeHandler}
                        />

                        <Button
                            color="primary"
                            type="submit"
                            fullWidth
                            variant="contained">
                            Завантажити
                        </Button>
                        {this.state.loading && <LinearProgress />}
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
export default withRouter(UploadLabForUserComponent);