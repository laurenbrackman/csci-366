# CSCI 366 - Fall 2021

This is the root folder for the CSCI 366 class project

The class is broken up into three sections:

* The CPU & Assembly in `/assembly`
* C Programming in `/c`
* Cloud Computing in `/cloud`

You will see there are three folders corresponding to these three areas in this
directory, as well as a grading folder that is used to grade your projects.

## Getting Your Private Copy

Please use the following steps to create a *private* copy of this repo for your work:

- Create a *private* repository in your own account by
    - Going to <https://github.com/new>
    - Enter the name `csci-366-fall2021-private`
    - Select `Private`
    - Navigate to the `Settings` -> `Manage Access` section
    - Add `1cg` as a collaborator
- Now run the following git commands, substituting your Github user name and NetID where required:
```bash
$ git clone https://github.com/msu/csci-366-fall2021.git csci-366-fall2021-private
$ cd csci-366-fall2021-private
$ git remote set-url origin git@github.com:<YOUR GITHUB USER NAME>/csci-366-fall2021-private.git
$ git remote add upstream https://github.com/msu/csci-366-fall2021.git
$ git push origin master
```
 

