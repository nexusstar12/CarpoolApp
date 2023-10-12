import { useContext } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import Popup from "reactjs-popup";
import { UserContext } from "../App";

const popupContentStyle = {
  color: "blue", // Change text color
  background: "rgb(242, 243, 245)", // Change background color (red with 50% opacity)
};

export default function Bar() {
  const userContext = useContext(UserContext);
  const isLoggedIn = userContext.userInfo?.email;
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Popup
            trigger={<Button color="inherit">Post a pool</Button>}
            position="right center"
            contentStyle={popupContentStyle}
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Popup
            trigger={<Button color="inherit">Join a pool</Button>}
            position="right center"
            contentStyle={popupContentStyle}
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Popup
            trigger={<Button color="inherit">How it works</Button>}
            position="right center"
            contentStyle={popupContentStyle}
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Pool
          </Typography>
          <Link to="/">
            <Button color="inherit">Home</Button>
          </Link>

          {isLoggedIn ? (
            <Typography color="inherit">{userContext.userInfo.name}</Typography>
          ) : (
            <Link to="/signup">
              <Button color="inherit">Signup</Button>
            </Link>
          )}
          {isLoggedIn ? (
            <Link to="/logout">
              <Button color="inherit"> Logout</Button>
            </Link>
          ) : (
            <Link to="/signin">
              <Button color="inherit"> Login</Button>
            </Link>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
