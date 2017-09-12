Frontend Tests
=============

1 Getting Started
--------------------
- [Jest](https://facebook.github.io/jest/) is our test runner.
- [Enzyme](http://airbnb.io/enzyme/) is a testing utility for React that makes it easier to assert, manipulate, and traverse your React Components' output.
- [Unit testing your React application with Jest and Enzyme (tutorial)](https://medium.com/wehkamp-techblog/unit-testing-your-react-application-with-jest-and-enzyme-81c5545cee45)
- [Testing React Components Best Practices (tutorial)](https://medium.com/selleo/testing-react-components-best-practices-2f77ac302d12)


If this is your first time working with front-end tests, run: ```npm install --save-dev jest enzyme```

Use ```npm test``` to run the tests or ```npm test -- --coverage``` to also generate the coverage report.
Coverage reports live at: ```/Frontend/coverage/lcov-report/index.html```

To ignore files in the coverage report(ie:ignore all milestone files): In file: '/Frontend/node_modules/react-scripts/utils/createJestConfig.js'. Add 'coveragePathIgnorePatterns: []' to the config dict.

2 Mock Functions
----------------
Use Mock Functions to spy on the behavior of a function that is called indirectly by some other code,
rather than just testing the output. Check the [Mock Functions Example in Jest](https://facebook.github.io/jest/docs/mock-function-api.html).

I think that the mock function is the most important tech we can use in the test. 
With mock functions, we can focus on the file/code itself, rather than some imported/depend functions
For example, we have two files:
A.js and B.js.
```js
// A.js
export const functionA = (number) => {
 //some complicated code
 return {
   value1: 1,
   value2: 2,
   value3: 3,
   ...
  };
 }
```
```js
// B.js
import { functionA } from './A'
const functionB = (n) => {
  const result = functionA(n)
  return {values: n}
} 
```
In that case, when writing the test for B.js, we should mock the functionA in B.test.js. 
The benefit of mocking dependence functions:
1. We are writing a test for B.js, and we just need to make sure that in functionB, functionA has been called and return the value to the variable result. We don't need to test the functionality of the functionA in B.test.js. 
2. The return value of functionA is a mess. We can mock the functionA, and just let the mock functionA return the input number.
3. In the future, if we modify functionA, we don't need to change the test for functionB.

3 Examples
----------

### 3.1 Action Creator Test 
We start with action creator since you can learn how mock function works in the test for action creators.
Take a look at [test example](http://redux.js.org/docs/recipes/WritingTests.html#connected-components)
 in the redux document to get the general idea.

We have **sync** actions and **async** actions in our code, the way to test them are different.

#### 3.1.1 Sync Action Creators: 
Sync Action Creators are functions which return plain objects. You can test Sync Action Creator by following the redux test example.

Or you also can do the test in another way:
##### Example:
```js
// src/actions/index.js
export const loginError = errorMessage => ({
  type: 'LOGIN_ERROR',
  errorMessage,
});
```
can be tested like:
```js
// src/actions/index.test.js
import * as action from './index';
import configureMockStore from 'redux-mock-store';

/* global it describe expect beforeEach jest afterEach*/

describe('Synchronous actions in index', () => {
  it('loginError', () => {
    const mockStore = configureMockStore([]);
    const initialState = {};
    const store = mockStore(initialState);
    
    store.dispatch(action.loginError('errorMessageValue'));
    const expectedAction = {
      type: 'LOGIN_ERROR',
      errorMessage: 'errorMessageValue',
    };
    const actions = store.getActions();
    expect(actions).toEqual([expectedAction]);
  });
});
```

#### 3.1.2 Async Action Creators:
First, take a look at the redux test example for Async Action Creators. However, we don't use 
**mock** to mock HTTP requests. We use jest to mock the functions and modules.However, you can 
use mock if you like.
>For async action creators using Redux Thunk or other middleware, it's best to completely 
mock the Redux store for tests. You can apply the middleware to a mock store using redux-mock-store.

The only difference is that we mock functions differently.
Because we write almost all actions in the index.js, the mock function looks pretty complicated.
Here is an Example about how to mock functions in a file like index.js.
##### Example:
```js
// /src/actions/example.js
import { fetchErrorHandler, getJSON, postJSON } from '../utilities/fetch';
import { getApiUrl } from '../utilities/helpers';

/* eslint no-param-reassign: ["error", { "props": false }] */

export const easyLoadSomething = () =>
  dispatch =>
    (getJSON(`${process.env.REACT_APP_API_ROOT}/easyload/`)
      .then((d) => {
        dispatch({
          type: 'LOADING_SUCCESS',
          data: d,
        });
      })
    );

export const loadSomething = () =>
  (dispatch) => {
    dispatch({
      type: 'BEGIN_LOADING',
    });
    return getJSON(`${process.env.REACT_APP_API_ROOT}/load/`)
      .catch((response) => {
        if (response.status === 403) {
          dispatch({
            type: 'LOADING_ERROR',
          });
          return Promise.reject(null);
        }
        return Promise.reject(response);
      })
      .catch(fetchErrorHandler(dispatch))
      .then((d) => {
        dispatch({
          type: 'LOADING_SUCCESS',
          data: d,
        });
      });
  };

export const saveSomething = () =>
  (dispatch, getState) => {
    dispatch({ type: 'SAVING_SOMETHING' });

    const personState = getState().person;
    const data = {
      name: personState.name,
      age: personState.age,
    };
    return postJSON(getApiUrl('/save_something/'), data)
      .catch(fetchErrorHandler(dispatch))
      .then(() => {
        dispatch({ type: 'SAVED_DATA' });
      });
  };


```
The above file use two imported functions: **getJSON()** and **postJSON()**, which are from same module.
In the test, we need the mock function for getJSON can have different behavior when the URL is different.
And to cover all branch, we want:
1. For easyLoadSomething(), getJSON() function return a resolve promise once.'easyload'
2. For loadSomething(), getJSON() function return resolve promise once, return rejected promise twice .'load'
3. For saveSomething(),postJSON() function return resplved promise once. 'save_something'

can be tested like:

```js
// src/actions/example.test.js
import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import * as action from './example';

/* global it describe expect beforeEach jest afterEach*/
/* eslint import/no-extraneous-dependencies: ["error", {"devDependencies": true}] */

jest.mock('../utilities/fetch', () => {
  const getJSONFnEasyload = jest.fn()
    .mockReturnValueOnce(Promise.resolve('easy_load_value'))
    .mockReturnValue(Promise.reject('please add mockReturnValueOnce function in getJSONFnEasyload'));

  const getJSONFnLoad = jest.fn()
    .mockReturnValueOnce(Promise.resolve({
      value: 'load_something_info',
    }))
    .mockReturnValueOnce(Promise.reject({
      status: 403,
    }))
    .mockReturnValueOnce(Promise.reject({
      status: 500,
    }))
    .mockReturnValue(Promise.reject('please add mockReturnValueOnce function in getJSONFnLoad'));

  const getJSONFn = jest.fn().mockImplementation((value) => {
    if (value.includes('/easyload/')) {
      return getJSONFnEasyload();
    }
    if (value.includes('/load/')) {
      return getJSONFnLoad();
    }
    return Promise.reject('please add more case in getJSONFn');
  });
  const postJSONFn = jest.fn()
    .mockImplementationOnce((url, data) => {
      expect(data).toEqual({
        name: 'personName',
        age: 'personAge',
      });
      return Promise.resolve();
    })
    .mockImplementation(
      () => Promise.reject('please add mockReturnValueOnce function in postJSONFn'),
    );

  const fetchErrorHandlerFn = jest.fn().mockImplementation(() => e => Promise.reject(e));

  return ({
    getJSON: getJSONFn,
    postJSON: postJSONFn,
    fetchErrorHandler: fetchErrorHandlerFn,
  });
});

describe('example.js: Async actions, easyLoadSomething()', () => {
  const middlewares = [thunk];
  const mockStore = configureMockStore(middlewares);
  let store;

  beforeEach(() => {
    const initialState = {};
    store = mockStore(initialState);
  });
  it('easyLoadSomething', () => {
    const expectedAction = {
      type: 'LOADING_SUCCESS',
      data: 'easy_load_value',
    };
    return store.dispatch(action.easyLoadSomething()).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual([expectedAction]);
    });
  });
});

describe('example.js: Async actions, loadSomething()', () => {
  const middlewares = [thunk];
  const mockStore = configureMockStore(middlewares);
  let store;

  beforeEach(() => {
    const initialState = {};
    store = mockStore(initialState);
  });

  it('loadSomething return a resolve promise', () => {
    const expectedAction1 = { type: 'BEGIN_LOADING' };
    const expectedAction2 = {
      type: 'LOADING_SUCCESS',
      data: {
        value: 'load_something_info',
      },
    };
    return store.dispatch(action.loadSomething()).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual([expectedAction1, expectedAction2]);
    });
  });

  it('loadSomething, reject status = 403', () => {
    const expectedAction1 = { type: 'BEGIN_LOADING' };
    const expectedAction2 = { type: 'LOADING_ERROR' };
    return store.dispatch(action.loadSomething())
      .catch((d) => {
        // return of async actions
        expect(store.getActions()).toEqual([expectedAction1, expectedAction2]);
        expect(d).toBe(null);
      });
  });

  it('loadSomething, reject status = 500', () => {
    const expectedAction1 = { type: 'BEGIN_LOADING' };
    return store.dispatch(action.loadSomething())
      .catch((response) => {
        // return of async actions
        expect(store.getActions()).toEqual([expectedAction1]);
        expect(response).toEqual({ status: 500 });
      });
  });
});

describe('example.js: Async actions saveSomething()', () => {
  const middlewares = [thunk];
  const mockStore = configureMockStore(middlewares);
  let store;

  beforeEach(() => {
    const initialState = {};
    store = mockStore(initialState);
  });
  it('saveSomething', () => {
    const initialState = {
      person: {
        name: 'personName',
        age: 'personAge',
        address: 'personAddress',
      },
    };
    store = mockStore(initialState);

    const expectedAction1 = { type: 'SAVING_SOMETHING' };
    const expectedAction2 = { type: 'SAVED_DATA' };
    return store.dispatch(action.saveSomething()).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual([expectedAction1, expectedAction2]);
    });
  });
});

```
### 3.2 Component Test
To test component, you need to spend some time on **Enzyme**'s APIs:
- [Shallow Rendering API](http://airbnb.io/enzyme/docs/api/shallow.html#shallow-rendering-api)
- [Full Rendering API](http://airbnb.io/enzyme/docs/api/mount.html#full-rendering-api-mount)
Things need test in the component:
1. is the wrapper render succeeded?
2. does some function(imported or passed in) got called? with expected argument and expected times?
3. simulated some event(click, submit...)


### 3.3 Reducer Test
> A reducer should return the new state after applying the action to the previous state, and that's the behavior tested below.

Just follow the [redux example](http://redux.js.org/docs/recipes/WritingTests.html#reducers) here.

Besides, I suggest not only check the expected state return by the reducer, but also check the original state was not changed(the reducer should be pure function)
##### Example:
```javascript
const initState = {
  getFaqsSuccess: false,
  faqs: [],
};

function faqInfo(state = initState, action) {
  switch (action.type) {
    case 'RECEIVE_FAQ_INFO':
      return {
        ...state,
        getFaqsSuccess: true,
        faqs: action.info,
      };
    default:
      return state;
  }
}


export default faqInfo;

```
can be tested like:
```js
/* global it describe expect beforeEach jest*/
/* eslint import/no-extraneous-dependencies: ["error", {"devDependencies": true}] */
import reducer from './faq';

describe('faq reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual({
      getFaqsSuccess: false,
      faqs: [],
    });
  });

  it('should handle RECEIVE_FAQ_INFO', () => {
    const state = {
      getFaqsSuccess: false,
      faqs: [],
    };
    const action = {
        type: 'RECEIVE_FAQ_INFO',
        info: [1, 2, 3, 4],
      };
    const state1 = reducer(state, action);
    expect(state).toEqual({
      getFaqsSuccess: false,
      faqs: [],
    });// the expect check if the reducer is a pure function
    expect(state1).toEqual({
      getFaqsSuccess: true,
      faqs: [1, 2, 3, 4],
    });// the expect check if the reducer return the expected state
  });
  
});
```
### 3.4 Container Test
First check this: [test example](http://redux.js.org/docs/recipes/WritingTests.html#connected-components) in the redux document.
In the container test, we need to test 2 things, they are:
1. mapStateToProps
2. mapDispatchToProps
#### mapStateToProps test
mapStateToProps is just a function which take state, props parameters and return a dict.
#### mapDispatchToProps
mapStateToProps is just a function which take a dispatch function as  parameter and return a dict which store some functions.
You should mock a dispatch function. You should also mock those functions which excuted in the dispatch function(ex:loadUsers, getStatements).
then in the test, expect the mock dispatch function got called and got called with expected arguments. Also expect the mock function got called and called with expected
arguments.

##### Example:
```js
import { connect } from 'react-redux';
import { getPlans, loadUser } from '../actions';
import checkToken from '../actions/ForgotPassword';
import { ExampleComponent } from '../components/example';

const mapStateToProps = state => ({
  plans: state.plan.months,
});

const mapDispatchToProps = dispatch => ({
  loadUser: userId => dispatch(loadUser(userId)),
  getPlans: () => dispatch(getPlans()),
  checkToken: (email, token) => dispatch(checkToken(email, token)),
});
export {
  mapStateToProps as _mapStateToProps,
  mapDispatchToProps as _mapDispatchToProps,
};
const Container = connect(
  mapStateToProps,
  mapDispatchToProps,
)(ExampleComponent);

export default Container;

```
can be tested like:
```js
import {
  _mapStateToProps as mapStateToProps,
  _mapDispatchToProps as mapDispatchToProps,
} from './example';

import { getPlans, loadUser } from '../actions';
import checkToken from '../actions/ForgotPassword';

/* global it describe expect beforeEach jest mockReturnValue*/
/* eslint import/no-extraneous-dependencies: ["error", {"devDependencies": true}] */


// mock functions which will excuted in the dispatch
jest.mock('../actions', () => {
  const getPlansFn = jest.fn()
    .mockReturnValue('getPlans');
  const loadUserFn = jest.fn(userId => (`loadUser-${userId}`));
  return ({
    getPlans: getPlansFn,
    loadUser: loadUserFn,
  });
});

jest.mock('../actions/ForgotPassword', () => {
  const checkTokenFn = jest.fn(((email, token) => (`checkToken-${email}-${token}`)));
  return checkTokenFn;
});


describe('Example->mapStateToProps', () => {
  it('mapStateToProps', () => {
    const state = {
      plan: {
        months: 'months-Value',
      },
    };
    expect(mapStateToProps(state)).toEqual({
      plans: 'months-Value',
    });
  });
});

describe('Example-> mapDispatchToProps', () => {
  const dispatchFn = jest.fn();
  beforeEach(() => {
    dispatchFn.mockClear();
    getPlans.mockClear();
    loadUser.mockClear();
  });

  it('loadUser', () => {
    const { loadUser: loadUserFn } = mapDispatchToProps(dispatchFn);
    expect(dispatchFn).toHaveBeenCalledTimes(0);
    loadUserFn('userId-value');

    expect(loadUser).toHaveBeenCalledTimes(1);
    expect(loadUser).toHaveBeenCalledWith('userId-value');

    expect(dispatchFn).toHaveBeenCalledTimes(1);
    expect(dispatchFn).toHaveBeenCalledWith('loadUser-userId-value');
  });

  it('getPlans', () => {
    const { getPlans: getPlansFn } = mapDispatchToProps(dispatchFn);
    expect(dispatchFn).toHaveBeenCalledTimes(0);
    getPlansFn();

    expect(getPlans).toHaveBeenCalledTimes(1);
    expect(getPlans).toHaveBeenCalledWith();

    expect(dispatchFn).toHaveBeenCalledTimes(1);
    expect(dispatchFn).toHaveBeenCalledWith('getPlans');
  });

  it('checkToken', () => {
    const { checkToken: checkTokenFn } = mapDispatchToProps(dispatchFn);
    expect(dispatchFn).toHaveBeenCalledTimes(0);
    checkTokenFn('emailValue', 'tokenValue');

    expect(checkToken).toHaveBeenCalledTimes(1);
    expect(checkToken).toHaveBeenCalledWith('emailValue', 'tokenValue');

    expect(dispatchFn).toHaveBeenCalledTimes(1);
    expect(dispatchFn).toHaveBeenCalledWith('checkToken-emailValue-tokenValue');
  });

});

```
