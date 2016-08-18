## Submission Guidelines

### Submitting a Pull Request (PR)

Before you submit your PR, consider the following guidelines:

* Ensure your changes is made in a new git branch:

     ``` shell
     git checkout -b feature/my_feature_branch master
     ```

* Create your feature or patch, **including appropriate test cases**.

* Run the full test suite and ensure that all tests pass.
  
* Commit your changes using a descriptive commit message.

     ``` shell
     git commit -a
     ```
  Note: the optional commit `-a` command line option will automatically "add" and "rm" edited files.

* Push your branch to GitHub:

    ``` shell
    git push origin feature/my_feature_branch
    ```

* In GitHub web UI, create PR to `play2-scala-pdf:master`.

* Ask a core team member to review your changes, if changes are suggested then:
  * Make the required updates.
  * Re-run the test suites to ensure tests are still passing.
  * Commit your changes to your branch (e.g. `feature/my_feature_branch`).
  * Pull latest `master` to check if there has been any further updates, merge (and/or)
    rebase if necessary (see note below).
  * Push the changes to your GitHub repository (this will update your PR).

If the PR gets too outdated, you will need to rebase and force push to update the PR:

``` shell
git rebase master -i
git push origin feature/my_feature_branch -f
```

*WARNING. Squashing or reverting commits and forced push thereafter may remove GitHub comments
on code that were previously made by you and others in your commits.*

#### After Your PR is Merged

After your PR is merged, delete your branch from GitHub (and locally), and pull the changes
from the main (upstream) repository:

* Delete the remote branch on GitHub either through the GitHub web UI or your local shell as follows:

    ``` shell
    git push origin --delete feature/my_feature_branch
    ```

* Check out the master branch:

    ``` shell
    git checkout master -f
    ```

* Delete the local branch:

    ``` shell
    git branch -D feature/my_feature_branch
    ```

* Update your master with the latest upstream version:

    ``` shell
    git pull --ff --prune upstream master
    ```