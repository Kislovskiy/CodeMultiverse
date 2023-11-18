function handleClick() {
  alert("I've been clicked!");
}
function Buttion() {
  return /*#__PURE__*/React.createElement("button", {
    onClick: handleClick
  }, "Click me!");
}
function MyApp() {
  return /*#__PURE__*/React.createElement("div", null, /*#__PURE__*/React.createElement("h1", null, "Welcome to my app"), /*#__PURE__*/React.createElement(Buttion, null));
}
