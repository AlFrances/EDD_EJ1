# EDD_EJ1 – Calculator (Version Control Assignment)

Java + HTML application that performs **addition, subtraction, multiplication and powers** using integers of up to 5 digits (-99999 to 99999).

- `Calculadora.java` → backend: a Java HTTP server (port 8080) that serves the web page and performs the operations
- `index.html` → frontend: the calculator form, which sends the numbers to the Java server

## How to run it

```bash
javac Calculadora.java
java Calculadora
```

Then open http://localhost:8080 in the browser. Stop the server with Ctrl+C.

Author: AlFrances

## Branches

| Branch | Content |
|--------|---------|
| `main` | Only comment/documentation updates and merges (via Pull Requests) |
| `backend` | Only `.java` commits |
| `frontend` | Only `.html` commits |

## How to publish a repository on GitHub

1. Go to github.com and click **New repository**.
2. Enter a name (`EDD_EJ1`), set it to **Public** and tick **Add a README file**.
3. Click **Create repository**.
4. Click the green **Code** button and copy the HTTPS URL.
5. Clone it on your computer with `git clone <URL>`.
6. Create branches, add files, commit and upload them with `git push`.
7. Merge each branch into `main` with a **Pull Request** on GitHub (Pull requests → New pull request → base: main, compare: branch → Create → Merge).
8. To submit, copy the URL of the public repository: https://github.com/AlFrances/EDD_EJ1

## Commands used (in chronological order)

```bash
# 1. Go to my working folder
cd C:\DAW\EDD

# 2. Set my Git identity
git config --global user.name "AlFrances"
git config --global user.email "frances.doydoy19@gmail.com"

# 3. Download my GitHub repository (created with a README.md)
git clone https://github.com/AlFrances/EDD_EJ1.git

# 4. Enter the folder and open it in VS Code
cd EDD_EJ1
code .

# 5. Create the backend and frontend branches from main
git branch backend
git branch frontend

# 6. List the branches
git branch

# 7. Switch to backend and check the current branch
git checkout backend
git status

# 8. (create Calculadora.java) Stage, commit and upload it
git status
git add Calculadora.java
git commit -m "Add Java calculator"
git push -u origin backend

# 9. Switch to frontend and check the current branch
git checkout frontend
git status

# 10. (create index.html) Stage, commit and upload it
git status
git add index.html
git commit -m "Add calculator web interface"
git push -u origin frontend

# (On GitHub: Pull request backend -> main, merged)
# (On GitHub: Pull request frontend -> main, merged)

# 11. Go back to main and download the merges
git checkout main
git pull origin main
git status

# 12. (update README.md) Commit and upload it
git status
git add README.md
git commit -m "Update README with commands used"
git push origin main

# 13. Show the history as a graph
git log --oneline --graph --all

# 14. Backend: turn Calculadora.java into a Java server (like the teacher's demo)
git checkout backend
git status
git add Calculadora.java
git commit -m "Add Java server to the calculator"
git push origin backend

# 15. Frontend: make index.html send the form to the Java server
git checkout frontend
git status
git add index.html
git commit -m "Connect calculator form to Java server"
git push origin frontend

# (On GitHub: Pull request backend -> main, merged)
# (On GitHub: Pull request frontend -> main, merged)

# 16. Download the merges into main
git checkout main
git pull origin main

# 17. Test the application
javac Calculadora.java
java Calculadora

# 18. Update the README on main
git status
git add README.md
git commit -m "Update README with server and new commands"
git push origin main
```

### What each command does

- **cd**: changes the current folder.
- **git config**: saves your name and email, which appear on every commit.
- **git clone**: copies a remote repository (GitHub) to your computer.
- **code .**: opens the current folder in VS Code.
- **git branch \<name\>**: creates a new branch; with no name, lists branches.
- **git checkout \<branch\>**: switches to another branch.
- **git status**: shows the current branch and which files changed.
- **git add**: stages files for the next commit.
- **git commit -m**: saves a snapshot of the staged changes with a message.
- **git push**: uploads local commits to GitHub (`-u` links the local branch to the remote one).
- **git pull**: downloads and merges changes from GitHub into the current branch.
- **Pull Request**: a GitHub request to merge one branch into another; once accepted, it creates a merge commit.
- **git log --graph**: shows the commit and branch history.
- **javac**: compiles a `.java` file into a `.class` file.
- **java**: runs the compiled Java program (here, it starts the server).