import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Box from '@material-ui/core/Box';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {API_URL} from "../constants/ApiConstants";
import axios from "axios";
import {Alert, AlertTitle} from "@material-ui/lab";

function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://material-ui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = makeStyles(theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));


export default function UploadLecture() {
    var errorMsg = ''
    function handle(event) {
        event.preventDefault()
        var apiBaseUrl = API_URL;
        var self = this;
        var formData = new FormData()
        formData.append("name",name)
        formData.append("presentation",file)
        axios.post(apiBaseUrl + 'lecture/upload', formData,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}})
            .then(function (response) {
                alert("Successful")
            }).catch(req => (errorMsg = 'failed!'))

    }
    function setFile(event) {
        file = event.target.files[0]
    }
    const [name, setName] = useState("");
    var [file] = useState(null);
    const classes = useStyles();

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>

                <Typography component="h1" variant="h5">
                    Завантажити лекцію
                </Typography>
                <form className={classes.form} onSubmit={event => handle(event)}>
                    {errorMsg ?
                        <Alert severity="error">
                            <AlertTitle>{ errorMsg}</AlertTitle>
                        </Alert> : <dir></dir>
                    }
                    <div><h1>{name}</h1></div>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        onChange={event => setName(event.target.value)}
                        fullWidth
                        id="email"
                        label="Назва"
                        name="name"
                        autoFocus
                    />
                    <Button
                        variant="contained"
                        component="label"
                    >
                        Обрати лекцію
                        <input
                            value={file}
                            onChange={event => setFile(event)}
                            type="file"
                            style={{ display: "none" }}
                        />
                    </Button>

                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                    >
                        Завантажити
                    </Button>
                </form>
            </div>
            <Box mt={8}>
                <Copyright />
            </Box>
        </Container>
    );
}