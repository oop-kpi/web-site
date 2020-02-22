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
import {CardHeader, Modal} from "@material-ui/core";


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




export default function Album() {
    const [card, setCard] = useState([]);
    const [selectedLecture, setSelectedLecture] = useState(null);
    let history = useHistory()
     useEffect(() => {
        const fetchData =  () => {
            const result = axios(
                API_URL+'lecture/getAll',{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}}).then(resp => setCard(resp.data)).catch(err => history.push({
                    pathname: '/login',
                    search: '?err=401',

                })
            )};
        fetchData();
    }, []);
    const classes = useStyles();

    function downloadPresentation(id) {

        axios({
            url: API_URL + 'lecture/download/' + id,
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

    return (
        <React.Fragment>
            <CssBaseline />
            <main>
                <Container className={classes.cardGrid} maxWidth="md">
                    {selectedLecture != null && <Modal aria-labelledby="transition-modal-title"
                                                       aria-describedby="transition-modal-description" className={classes.modal}  open={selectedLecture} onClose={(() => {setSelectedLecture(null)})}>
                        <Container maxWidth="md" className={classes.paper}>
                            <Card>
                                <CardHeader title={selectedLecture.name}></CardHeader>
                                <CardContent>
                                    {selectedLecture.description||"No desc provided"}
                                </CardContent>
                                <CardActions><Button onClick={event => downloadPresentation(selectedLecture.id)}>
                                    Download
                                </Button></CardActions>
                            </Card>
                        </Container>
                    </Modal>
                        }
                    {/* End hero unit */}
                    <Grid container spacing={4}>
                        {card.map(card => (
                            <Grid item key={card} xs={12} sm={6} md={4}>
                                <Card className={classes.card}>
                                    <CardMedia
                                        className={classes.cardMedia}
                                        image="https://source.unsplash.com/random"
                                        title= ""
                                    />
                                    <CardContent className={classes.cardContent}>
                                        <Typography gutterBottom variant="h5" component="h2">
                                            {card.name}
                                        </Typography>
                                        <Typography>
                                            This is a media card. You can use this section to describe the content.
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small" color="primary" onClick={event => setSelectedLecture(card)}>
                                            View
                                        </Button>
                                        <Button size="small" color="primary">
                                            Edit
                                        </Button>
                                    </CardActions>
                                </Card>
                            </Grid>
                        ))}
                        <Button href="/uploadLecture">Завантажити!</Button>
                    </Grid>

                </Container>
            </main>
        </React.Fragment>
    );
}