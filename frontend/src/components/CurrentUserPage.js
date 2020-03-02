
import React, { Component } from 'react';
import CssBaseline from '@material-ui/core/CssBaseline';
import {API_URL} from '../constants/ApiConstants'

import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import {Card, CardHeader, LinearProgress, Modal} from "@material-ui/core";
import CardContent from "@material-ui/core/CardContent";
import {Redirect} from "react-router";
import Container from "@material-ui/core/Container";
import axios from "axios";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import {styled} from "@material-ui/core/styles";
import Comment from "./Comment";
import EditLabPage from "./EditLabPage";

const MyButton = styled(Button)({
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
    margin: '20px'
});



class CurrentUserPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectLab:null,
            selectedLab:null,
            current:{},
            loading:false

        }
    }

    myChangeHandler = (event) => {
        this.setState({selectedLab:event});
    }
    componentDidMount() {
        let curr
            axios.get(API_URL+'user/me',{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(response => this.setState({current:response.data}))

    }
    updateLab = (lab) => {
        this.setState({redirectLab: lab})
    }
  stopLoading = () => {
        this.setState({loading:false})
    }

    downloadLab(id) {
        this.setState({loading:true})
        axios({
            url: API_URL + 'lab/download/' + id,
            method: 'GET',
            responseType: 'blob',
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            let headerLine = response.headers['content-disposition']
            let filename = headerLine.substring(21)
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', filename);
            document.body.appendChild(link);
            link.click();
            this.stopLoading()
        });

    }
    render() {


    if (this.state.current == null){
            return <Redirect to="login?err=401"></Redirect>
        }
    if (this.state.redirectLab != null){
            return <EditLabPage selectedLab={this.state.redirectLab}></EditLabPage>
        }




    return (

            <Container  maxWidth="md">
                    <CssBaseline/>
                    <CssBaseline/>
                    <Card raised="true">
                        {this.state.selectedLab != null &&
                            <Grid container
                                  direction="row" alignItems="center">
                        <Modal  aria-labelledby="transition-modal-title" aria-describedby="transition-modal-description" onClose={(() => {this.myChangeHandler(null)})} open>
                            <Container maxWidth="md" >
                                <Card>
                                    <CardHeader title={this.state.selectedLab.name}></CardHeader>
                                    <CardContent>
                                        <Typography >
                                            {this.state.selectedLab.mark<=0 ? "Не перевірена": "Оцінка:"+this.state.selectedLab.mark}
                                        </Typography>
                                        <CssBaseline/>
                                        <Typography variant="h5">
                                            Коментарі:
                                        </Typography>
                                        <br/>
                                        <CssBaseline/>
                                        <Comment comments={this.state.selectedLab.comments}/>
                                    </CardContent>
                                    <CardActions>  <Typography >
                                        {this.state.selectedLab.pathToFile.startsWith("http")? "Посилання:"+this.state.selectedLab.pathToFile : <MyButton onClick={(event)=>this.downloadLab(this.state.selectedLab.id)}>Завантажити</MyButton>}
                                    </Typography></CardActions>
                                    {this.state.loading && <LinearProgress />}
                                </Card>
                            </Container>
                        </Modal>
                            </Grid>
                        }
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
                                Пошта: {this.state.current.group}
                            </Typography>
                            <Typography variant="h5" component="h6">
                                Лабораторні роботи:
                            </Typography>
                            {this.state.current.laboratoryWorks!=null && this.state.current.laboratoryWorks.map(lab => (
                                lab&&
                                    <Grid
                                        item key={lab}
                                        container
                                        direction="column"
                                        style={{ minHeight: '18vh' }}
                                    >
                                    <Card>
                                        <CardContent>
                                            <Typography gutterBottom variant="h5" component="h2">
                                                {lab.name}
                                            </Typography>
                                            <Typography>
                                              {lab.mark<=0 ? "Не перевірена": "Оцінка:"+lab.mark}
                                            </Typography>
                                        </CardContent>
                                        <CardActions>
                                            <MyButton onClick={event => this.myChangeHandler(lab)}>Переглянути</MyButton>
                                            <MyButton onClick={(event)=>this.updateLab(lab)}>Редагувати</MyButton>
                                        </CardActions>
                                    </Card>
                                </Grid>
                            ))}
                    </CardContent>
                        <Grid container
                              direction="column" alignItems="center">

                            <MyButton href="/uploadLab">Завантажити</MyButton>
                        </Grid>
                    </Card>
              </Container>
        );
    }
}
export default CurrentUserPage;