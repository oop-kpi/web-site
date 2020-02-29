import React, {useState} from "react";
import { makeStyles } from "@material-ui/core/styles";
import {Card, CardHeader, Container, LinearProgress, Modal, TextField} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import axios from "axios";
import {API_URL} from "../constants/ApiConstants";

const useStyles = makeStyles({
    root: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        borderRadius: 3,
        border: 0,
        color: 'white',
        height: 48,
        padding: '0 30px',
        margin: '20px',
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    },
    text:{
        background: 'red'
    }
});

const EditLabPage = ({ selectedLab }) => {
    const classes = useStyles()
    const[name,setName] = useState(selectedLab.name)
    const[link,setLink] = useState(selectedLab.pathToFile? selectedLab.pathToFile:"")
    const[file,setFile] = useState(null)
    const[error,setError] = useState(null)
    const[loading,setLoading] = useState(false)


    function updateLab() {
        var formData = new FormData()
        formData.append("name",name)
        formData.append("id",selectedLab.id)

        if (file!=null){
            formData.append("file",file)
        } else if (link != ''){
            formData.append("link",link)
        } else {
            alert("Оберіть або файл, або посилання")
        }
        setLoading(true)
        axios.patch(API_URL + 'lab/update', formData,{headers: {'Authorization': 'Bearer '+localStorage.getItem('token')}})
            .then(response =>
                setLoading(false)

            ).catch(req => (setLoading(false),alert("Помилка!"+req.message)))
    }
    return (

             <Container maxWidth="md" >
                 <Card raised="true" style={{margin:"30px"}}>

                     <CardHeader title="Редагувати:"></CardHeader>
                     <CardContent>
                         <Typography  variant="h5" style={{color:"red"}}>
                             При зміні будь-якого параметра оцінка буде анульована!
                         </Typography>
                         <Typography variant="h6" component="h6">
                             Назва :<TextField fullWidth inputProps={{style:{fontSize: 25, fontFamily:"Helvetica"}}} onChange={event => setName(event.target.value)} value={name}></TextField>
                         </Typography>
                         <Typography variant="h6" component="h6">
                             Посилання: <TextField fullWidth inputProps={{style:{fontSize: 20}}} onChange={event => setLink(event.target.value)} value={link.startsWith("http")?link:""}></TextField>
                         </Typography>
                         <Typography variant="h6" component="h6">
                             Файл: <TextField  type="file"
                                              fullWidth
                                               onChange={(event => setFile(event.target.files[0]))}
                                               variant="outlined"
                                               margin="normal"></TextField>
                         </Typography>
                     </CardContent>
                     <Grid container direction="column" alignItems="center">
                         <Button color="primary" onClick={event => updateLab()} className={classes.root}>Завантажити</Button>
                     </Grid>
                     {loading && <LinearProgress />}
                 </Card>
             </Container>
    );
};

export default EditLabPage;
