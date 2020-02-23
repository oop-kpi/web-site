
import React, { Component } from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import {API_URL} from '../constants/ApiConstants'

import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import {Card, CardHeader} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import {Redirect} from "react-router";
import Container from "@material-ui/core/Container";
import axios from "axios";



class CurrentUserPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            current:{laboratoryWorks:[]}

        }
    }
    componentDidMount() {
        let curr
            axios.get(API_URL+'user/me',{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(response => this.setState({current:response.data}))

    }
    render() {
        if (this.state.current == null){
            return <Redirect to="login"></Redirect>
        }
        return (

            <Container  maxWidth="md">
                    <CssBaseline/>
                    <CssBaseline/>
                    <Card>
                   <CardHeader title="Про вас:"></CardHeader>
                        <CardContent>
                            <Typography variant="h6" component="h6">
                                Логін : {this.state.current.login}
                            </Typography>
                            <Typography variant="h6" component="h6">
                                Ім'я: {this.state.current.name}
                            </Typography>
                            <Typography variant="h6" component="h6">
                                Пошта: {this.state.current.email}
                            </Typography>
                            <Typography variant="h6" component="h6">
                                Лабораторні роботи:
                            </Typography>
                            {this.state.current.laboratoryWorks.map(lab => (
                                <Grid item key={lab} xs={12} sm={6} md={4}>
                                    <Card>

                                        <CardContent>
                                            <Typography gutterBottom variant="h5" component="h2">
                                                {lab.name}
                                            </Typography>
                                            <Typography>
                                              {lab.mark<=0 ? "Не перевірена": "Оцінка:"+lab.mark}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                </Grid>
                            ))}
                    </CardContent>
                    </Card>
              </Container>

        );
    }
}
export default CurrentUserPage;