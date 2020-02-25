import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
    List,
    ListItem,
    Divider,
    ListItemText,
    Typography
} from "@material-ui/core";

const useStyles = makeStyles(theme => ({
    root: {
        width: "100%",
        backgroundColor: theme.palette.background.paper
    },
    fonts: {
        fontWeight: "bold"
    },
    inline: {
        display: "inline"
    }
}));

const Comment = ({ comments }) => {
    const classes = useStyles();
    return (
        <List className={classes.root}>
            {comments.map(comment => {
                console.log("Comment", comment);
                return (
                    <React.Fragment key={comment.id}>
                        <ListItem key={comment.id} alignItems="flex-start">
                            <ListItemText
                                primary={
                                    <Typography className={classes.fonts}>
                                        {comment.owner}
                                        <br/>
                                        {comment.content}
                                    </Typography>
                                }


                            />
                        </ListItem>
                        <Divider />
                    </React.Fragment>
                );
            })}
        </List>
    );
};

export default Comment;
