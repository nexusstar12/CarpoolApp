import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { Link } from "react-router-dom";
import SearchBar from "./SearchBar";
import Dropdown from "./Dropdown";
import Popup from "reactjs-popup";

const popupContentStyle = {
  color: "blue", // Change text color
  background: "rgb(242, 243, 245)", // Change background color (red with 50% opacity)
};

export default function Bar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Popup
            trigger={<Button color="inherit">Post a pool</Button>}
            position="right center"
            contentStyle={popupContentStyle} // Apply custom styles to the popup content
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Popup
            trigger={<Button color="inherit">Join a pool</Button>}
            position="right center"
            contentStyle={popupContentStyle} // Apply custom styles to the popup content
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Popup
            trigger={<Button color="inherit">How it works</Button>}
            position="right center"
            contentStyle={popupContentStyle} // Apply custom styles to the popup content
          >
            <div color="red">Feature coming soon</div>
          </Popup>

          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Pool
          </Typography>
          <Link to="/">
            <Button color="inherit">Home</Button>
          </Link>
          <Link to="/signup">
            <Button color="inherit">Signup</Button>
          </Link>
          <Link to="/signin">
            <Button color="inherit">Login</Button>
          </Link>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
