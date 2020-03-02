import React, {useState} from 'react';
import axios from 'axios';
import {API_URL} from "../constants/ApiConstants";
import Modal from "@material-ui/core/Modal";
import Button from "@material-ui/core/Button";
import {CardHeader, Container, LinearProgress, ListItemText} from "@material-ui/core";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CssBaseline from "@material-ui/core/CssBaseline";
import Comment from "./Comment";
import CardActions from "@material-ui/core/CardActions";
import Grid from "@material-ui/core/Grid";
import {Redirect, withRouter} from "react-router";
import EditLabPage from "./EditLabPage";
import TextField from "@material-ui/core/TextField";
import {List} from "@material-ui/icons";
import ListItem from "@material-ui/core/ListItem";

class userPageComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
         user:null,
            editUser:null,
            redirectLab:null,
            selectedLab:null,
            loading:false,
            edit:true
        }
    }
    editLab(val){
        if (val){
            this.saveUserInformation();
        }
        this.setState({edit:val})
    }

    saveUserInformation() {
        const data = this.state.editUser;
        axios.patch(API_URL+"user/"+this.state.user.login+'/update',data,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(resp => alert("Оновлено!"))
    }
    myChangeHandler = (event) => {
        this.setState({selectedLab:event});
    }
    updateLab = (lab) => {
        this.setState({redirectLab: lab})
    }
    stopLoading = () => {
        this.setState({loading:false})
    }
    componentDidMount() {
        const {login } = this.props.match.params;
        axios.get(API_URL+'user/login/'+login,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}})
            .then(({ data: user }) => {
                this.setState({ editUser:user });
                this.setState({user:user});
            });
    }

    handleDelete() {
    }
    render() {
        if (this.state.redirectLab != null){
            return <EditLabPage selectedLab={this.state.redirectLab}></EditLabPage>
        }
      function downloadLab(id) {
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
        const { user } = this.state;
        const { editUser } = this.state;
        return (
            <Grid container direction="row" alignItems="center">
                    <Container maxWidth="md" >
                        {this.state.selectedLab != null &&
                        <Grid container
                              direction="row" alignItems="center">
                            <Modal aria-labelledby="transition-modal-title" aria-describedby="transition-modal-description" onClose={(() => {this.myChangeHandler(null)})} open>
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
                                            {this.state.selectedLab.pathToFile.startsWith("http")? "Посилання:"+this.state.selectedLab.pathToFile : <Button color="primary" onClick={(event)=>downloadLab(this.state.selectedLab.id)}>Завантажити</Button>}
                                        </Typography></CardActions>
                                        {this.state.loading && <LinearProgress />}
                                    </Card>
                                </Container>
                            </Modal>
                        </Grid>
                        }
                        {user ?<Card>
                            <CardHeader title={"Інформація про "+user.login+" :"}></CardHeader>
                            <CardContent>
                                <Grid container
                                    direction="column" alignItems="center">
                             <p>ПІБ</p><TextField  InputProps={{
                                 readOnly: this.state.edit,
                             }} value={editUser.name} name="name" onChange={event => this.userChangeHandler(event)}></TextField>
                                <p>Логін</p><TextField InputProps={{
                                    readOnly: this.state.edit,
                                }} name="login" value={editUser.login} onChange={event => this.userChangeHandler(event)}></TextField>
                              <p>Пошта</p> <TextField InputProps={{
                                    readOnly: this.state.edit,
                                }}  name="email" value={editUser.email} onChange={event => this.userChangeHandler(event)}></TextField>
                               <p>Группа</p><TextField InputProps={{
                                    readOnly: this.state.edit,
                                }}  name="group" value={editUser.group} onChange={event => this.userChangeHandler(event)}></TextField>
                                 <p>Бал</p><TextField InputProps={{
                                    readOnly: this.state.edit,
                                }}  name="ball" value={editUser.ball} onChange={event => this.userChangeHandler(event)}></TextField>
                                    <h4>Ролі:</h4>
                                    {editUser.roles.map(role =>
                                        (<li>
                                            {role == 'ROLE_TEACHER'?"Вчитель": role =='ROLE_REVIEWER'? 'Переглядач':'Учень'}
                                    </li>))}

                                    <Button style={{margin:'20px'}} variant="contained" color="primary" onClick={event => this.editLab(!(this.state.edit))}>
                                        {this.state.edit ? "Редагувати":"Зберегти" }
                                    </Button>
                                </Grid>
                            </CardContent>
                            {user.laboratoryWorks!=null && user.laboratoryWorks.map(lab => (
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
                                            <Button color="primary" onClick={event => this.myChangeHandler(lab)}>Переглянути</Button>
                                            <Button color="primary" onClick={(event)=>this.updateLab(lab)}>Редагувати</Button>
                                        </CardActions>
                                    </Card>

                                </Grid>
                            ))}
                            <Button variant="contained" color="primary" fullWidth style={{margin:"20px"}} onClick={event => {this.uploadLab()}}>Завантажити</Button>

                        </Card>: <h3>Неправильний логін!</h3> }
                    </Container>
            </Grid>
        );
    }

    handleRedirect() {
        document.location.href = '/browse-users'
    }

    userChangeHandler(event) {
        let userCopy = JSON.parse(JSON.stringify(this.state.editUser))
        userCopy[event.target.name] =  event.target.value
            this.setState({
                editUser:userCopy
            })
    }

    uploadLab() {
        this.props.history.push("/user/"+this.state.editUser.login+"/createLab")
    }
}

export default withRouter(userPageComponent);