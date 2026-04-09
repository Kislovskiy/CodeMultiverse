function handleClick() {
  alert("I've been clicked!");
}
function Buttion() {
  return /*#__PURE__*/React.createElement("button", {
    onClick: handleClick
  }, "Click me!");
}
function AboutPage() {
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("h1", null, "About"), /*#__PURE__*/React.createElement("p", null, "Hello there.", /*#__PURE__*/React.createElement("br", null), "How do you do?"));
}
function MyApp() {
  return /*#__PURE__*/React.createElement("div", null, /*#__PURE__*/React.createElement("h1", null, "Welcome to my app"), /*#__PURE__*/React.createElement(Buttion, null), /*#__PURE__*/React.createElement(AboutPage, null));
}

