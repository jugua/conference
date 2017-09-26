import React from "react";
import { shallow } from "enzyme";

import Foo from "./index";

jest.unmock("./index");

describe("<Foo />", () => {
  it("renders a <p> with a static text", () => {
    const wrapper = shallow(<Foo />);
    expect(wrapper.contains(<p>I am not a very smart component...</p>)).toBe(true);
  });
});
