import React, { useContext, useState } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Popup from "reactjs-popup";
import { UserContext } from "../App";
import { useNavigate } from "react-router-dom";

const popupContentStyle = {
  color: "blue",
  background: "rgb(242, 243, 245)",
};

export default function Bar({ isDriver }) {
  const history = useNavigate();
  const userContext = useContext(UserContext);
  const isLoggedIn = userContext.userInfo?.email;
  const [anchorEl, setAnchorEl] = useState(null);

  const handleOpenMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    userContext.setUserInfo(null);
    history("/");
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Link
            to="/create-pool"
            style={{ textDecoration: "none", margin: "0 8px" }}
          >
            <Button
              sx={{
                color: "#fff",
                backgroundColor: "transparent",
                fontWeight: "bold",
                "&:hover": { background: "rgba(255,255,255,0.08)" },
              }}
            >
              Post a pool
            </Button>
          </Link>

          <Link to="/" style={{ textDecoration: "none", margin: "0 8px" }}>
            <Button
              color="inherit"
              sx={{
                fontWeight: "bold",
                color: "#fff",
                "&:hover": { background: "rgba(255,255,255,0.08)" },
              }}
            >
              Join a pool
            </Button>
          </Link>

          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Pool
          </Typography>

          <Link to="/" style={{ textDecoration: "none", margin: "0 8px" }}>
            <Button
              sx={{
                fontWeight: "bold",
                color: "#fff",
                "&:hover": { background: "rgba(255,255,255,0.08)" },
              }}
            >
              Home
            </Button>
          </Link>

          {isLoggedIn && (
            <Link
              to="/my-pools"
              style={{ textDecoration: "none", margin: "0 8px" }}
            >
              <Button
                sx={{
                  color: "#fff",
                  fontWeight: "bold",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                My Pools
              </Button>
            </Link>
          )}
          {isLoggedIn && (
            <Link
              to="/my-crews"
              style={{ textDecoration: "none", margin: "0 8px" }}
            >
              <Button
                sx={{
                  color: "#fff",
                  fontWeight: "bold",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                My Crews
              </Button>
            </Link>
          )}

          {isLoggedIn ? (
            <div>
              <Link
                to="/passenger-profile"
                style={{ textDecoration: "none", margin: "0 8px" }}
              >
                <Button
                  color="inherit"
                  aria-controls="profile-menu"
                  aria-haspopup="true"
                  onClick={handleOpenMenu}
                  sx={{
                    fontWeight: "bold",
                    color: "#fff",
                    "&:hover": { background: "rgba(255,255,255,0.08)" },
                  }}
                >
                  My Profile
                </Button>
              </Link>

              {isDriver && (
                <Menu
                  id="profile-menu"
                  anchorEl={anchorEl}
                  keepMounted
                  open={Boolean(anchorEl)}
                  onClose={handleCloseMenu}
                >
                  <MenuItem>
                    <Link
                      to="/passenger-profile"
                      style={{ textDecoration: "none", color: "inherit" }}
                    >
                      Passenger Profile
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <Link
                      to="/driver-profile"
                      style={{ textDecoration: "none", color: "inherit" }}
                    >
                      Driver Profile
                    </Link>
                  </MenuItem>
                </Menu>
              )}
            </div>
          ) : (
            <Link
              to="/signup"
              style={{ textDecoration: "none", margin: "0 8px" }}
            >
              <Button
                sx={{
                  color: "#fff",
                  fontWeight: "bold",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                Signup
              </Button>
            </Link>
          )}

          {isLoggedIn ? (
            <Button
              color="inherit"
              onClick={handleLogout}
              sx={{
                fontWeight: "bold",
                "&:hover": { background: "rgba(255,255,255,0.08)" },
              }}
            >
              Logout
            </Button>
          ) : (
            <Link
              to="/signin"
              style={{ textDecoration: "none", margin: "0 8px" }}
            >
              <Button
                sx={{
                  color: "#fff",
                  fontWeight: "bold",
                  "&:hover": { background: "rgba(255,255,255,0.08)" },
                }}
              >
                Login
              </Button>
            </Link>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
