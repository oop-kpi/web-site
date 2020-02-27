import React, {useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import axios from "axios";
import {API_URL} from "../constants/ApiConstants";
import { useHistory } from "react-router-dom";
import {CardHeader, Modal, TextField} from "@material-ui/core";
import Comment from "./Comment";


const useStyles = makeStyles(theme => ({
    icon: {
        marginRight: theme.spacing(2),
    },
    heroContent: {
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(8, 0, 6),
    },
    heroButtons: {
        marginTop: theme.spacing(4),
    },
    cardGrid: {
        paddingTop: theme.spacing(8),
        paddingBottom: theme.spacing(8),
    },
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    card: {
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
    },
    cardMedia: {
        paddingTop: '56.25%', // 16:9
    },
    cardContent: {
        flexGrow: 1,
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
    footer: {
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(6),
    },
}));




export default function LabsPoolComponent() {
    const [card, setCard] = useState([]);
    const [selectedLab, setSelectedLecture] = useState(null);
    const [user,setUser] = useState(null)
    const [content,setContent] = useState("")
    const [ball,setBall] = useState(0)
    const fetchData =  () => {
        setUser(JSON.parse(localStorage.getItem('user')))
        axios.get(
            API_URL+'lab/getLabsToEvaluate',{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(resp => setCard(resp.data)).catch(err => history.push({
                pathname: '/login',
                search: '?err=401',
            })
        )};
    let history = useHistory()
    useEffect(() => {
        fetchData();
    }, []);
    const classes = useStyles();

    function downloadLab(id) {
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
        });
    }
    function evaluate(labIds) {
        var comment = {
            comment:content,
            labId:labIds,
            mark:ball
        }
        axios.post(API_URL+"lab/evaluate",comment,{headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}}).then(resp=>setSelectedLecture(null))
        window.location.reload(true)
    }

    function comment(labIds) {
        var comment = {
            content:content,
            labId:labIds
        }
        axios.post(API_URL+"lab/comment",comment,{headers: {'Authorization': 'Bearer ' + localStorage.getItem('token')}}).then()
        window.location.reload(true)
    }

    function decide(labid) {
        if (user.roles.includes("ROLE_TEACHER")){
            evaluate(labid)
        } else{
            comment(labid)
        }
    }

    return (
        <React.Fragment>
            <CssBaseline />
            <main>
                <Container className={classes.cardGrid} maxWidth="md">
                    {selectedLab != null && <Modal aria-labelledby="transition-modal-title"
                                                       aria-describedby="transition-modal-description" className={classes.modal}  open={selectedLab} onClose={(() => {setSelectedLecture(null)})}>
                        <Container maxWidth="md" className={classes.paper}>
                            <Card>
                                <CardHeader title={selectedLab.name}></CardHeader>
                                <CardContent>
                                    <Typography>
                                        Виконав: {selectedLab.user.name}
                                        <br/>
                                        Група: {selectedLab.user.group}
                                        <br/>
                                    </Typography>
                                    {selectedLab.pathToFile.startsWith("http")?<div> Посилання: <a href={selectedLab.pathToFile}>{selectedLab.pathToFile}</a></div>:<Button onClick={(event) => downloadLab(selectedLab.id)} color="primary">Завантажити</Button>}
                                    <Typography variant="h4" >
                                        Коментарі:
                                    </Typography>
                                    <CssBaseline/>
                                    <Comment comments={selectedLab.comments}/>
                                    <div>
                                        <Typography>
                                            {user.roles.includes('ROLE_TEACHER')? "Оцінити" : "Коментувати"}
                                        </Typography>
                                        <TextField type="text" name="content" multiline fullWidth onChange={(event)=>setContent(event.target.value)} label="Введіть коментарій"></TextField>
                                        <br/>
                                        {user.roles.includes('ROLE_TEACHER') && <TextField onChange={(event) => setBall(event.target.value) } label="Введіть оцінку">0</TextField> }
                                        <br/>
                                        <Button onClick={()=>decide(selectedLab.id)} color="primary" fullWidth >Відправити!</Button>
                                    </div>
                                </CardContent>
                                <CardActions>

                                </CardActions>
                            </Card>
                        </Container>
                    </Modal>
                    }
                    {/* End hero unit */}
                    {card != null &&
                        <Grid container spacing={4}>
                            {card.map(card => (
                                <Grid item key={card} xs={12} sm={6} md={4}>
                                    <Card className={classes.card}>
                                        <CardMedia
                                            className={classes.cardMedia}
                                            image="https://source.unsplash.com/random"
                                            title=""
                                        />
                                        <CardContent className={classes.cardContent}>
                                            <Typography gutterBottom variant="h5" component="h2">
                                                {card.name}
                                            </Typography>
                                            <Typography>
                                                Виконав: {card.user.name}
                                                <br/>
                                                Група: {card.user.group}
                                            </Typography>
                                        </CardContent>
                                        <CardActions>
                                            <Button size="small" color="primary"
                                                    onClick={event => setSelectedLecture(card)}>
                                                Переглянути
                                            </Button>
                                            <Button size="small" color="primary">
                                            </Button>
                                        </CardActions>
                                    </Card>
                                </Grid>
                            ))}
                        </Grid>}
                        </Container>
            </main>
        </React.Fragment>
    );
}