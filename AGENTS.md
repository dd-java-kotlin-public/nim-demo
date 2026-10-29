# Agent Guidelines for the Learner

## Precedence and Conflicts

- Resolve conflicting instructions in this order: the current prompt; the project-level `AGENTS.md`; the root `AGENTS.md` (i.e., `~/AGENTS.md`); vendor-specific instruction files and skill definitions; agent defaults.
- Vendor adapters and skills implement the root `AGENTS.md`; they do not override it.
- Stop and report a conflict that this precedence order does not resolve.

## Scope and Change Discipline

- Work only on the task in the current request. Ask before acting when the correct scope is unclear.
- When asked for advice, analysis, an explanation, or a recommendation, provide only that. Do not modify files until the requester explicitly asks for an implementation.
- Make the smallest coherent change that satisfies the request. Do not include unrelated formatting, refactoring, reorganization, or documentation changes.
- Preserve existing formatting, line endings, and character encoding. Match the conventions of the surrounding project.
- Do not create scripts, notes, configuration, or other files that the request does not require.
- Keep the work understandable to the requester.

## Protected Work

Do not modify, move, or delete the following unless the current request explicitly names the affected item and asks for that change:

- Build and toolchain files.
- Dependency declarations and dependency versions.
- Repository metadata, including `LICENSE`, `README.md`, and `.gitignore`.
- Agent instructions, configuration, capability grants, context-exclusion
  files, and automation workflows.
- Any file or other project element that the accompanying documentation identifies as provided or fixed.

Treat a file as agent configuration when it resides in an agent-vendor directory, has a name associated with an agent product, or exists primarily to direct or grant capabilities to an agent.

## Provided Contracts

- Unless explicitly directed to change them, preserve every provided declaration exactly, including its name, modifiers, type parameters, parameters, return type, and thrown types.
- Use only the dependency set and versions supplied by the project.

## Verification and Reporting

- Run the relevant project checks after changing code. If a check cannot run, report why and identify the result as unverified.
- Do not modify, delete, disable, or weaken a provided test to make it pass. Correct the implementation instead. If a provided test appears incorrect, stop and report the issue.
- Do not hardcode expected values, special-case test inputs, or stub a member to satisfy a check.
- Report the command used and the result actually observed.
- Do not state that code compiles, tests pass, or a program runs without observing that result in the current session.
- Report failures as failures and include the relevant output.
- Distinguish observed facts from assumptions and recommendations.
- If safe, complete work is blocked, report the blocker. Do not silently substitute a partial or placeholder solution.

## Version-Control Safety

- Check `git status` before changing repository content and whenever the requester asks for a status check.
- Treat staged, unstaged, and untracked changes that predate the current task as requester-owned work. Do not attribute them to the current task or combine them with a commit for the current task.
- Use explicit pathspecs when staging or committing. Do not use `git add -A`, `git add .`, or `git commit -a`.
- Do not push, rewrite history, change branches or remotes, create or alter pull requests, manipulate stashes, or discard changes unless the current request explicitly asks for that operation.

## Clean-Worktree Requirement

- Before making any repository content change, run `git status --porcelain=v1 --untracked-files=all`.
- If the command reports any staged, unstaged, or untracked path, stop before modifying files and report the existing paths to the requester.
- Ask the requester to commit, stash, or discard the existing changes. Do not stage, commit, stash, restore, or otherwise alter those changes on the requester's behalf.
- Resume only after the requester provides a working tree for which the status command produces no output.

## Agent-Managed Git Workflow

- Treat a request to change repository content as authorization to stage and propose changes, but do not create a commit until the requester accepts the proposed diff.
- Do not create a commit when the request produces no repository content changes.
- Before committing, present a concise diff report or summary of modified paths, explain how the changes satisfy the request, and report observed verification results.
- If the requester rejects the proposed changes or asks to discard them:
    - Discard only the changes and untracked files produced during the current task.
    - Return the working tree to the clean state that existed prior to the task.
    - Do not alter or discard any work predating the current task.
- If the requester accepts the changes presented in the diff report, commit each coherent change after verification:
    - Prefix its subject with `Changes by {agent}: `.
    - Keep each subject line to 72 characters or fewer.
    - After a blank line, add a summary proportionate to the change. Explain why the requested change was made, not only what changed.
    - For a commit made in response to a request, add another blank line followed by `Prompt: ` and the prompt that led to the change on the same line.
    - Add any identity trailers required by the active agent environment after the required content.
    - After committing, verify that the commit contains exactly the intended paths and report the resulting working-tree state.

## Irreversible and Destructive Operations

Do not perform any of the following unless the current request explicitly and specifically asks for the operation:

- Rewrite history with `git commit --amend`, `git rebase`, `git reset --hard`, any `git filter-*` command, or a force-push.
- Push to a remote or create, merge, or close a pull request.
- Delete or rename a branch, or drop or clear a stash.
- Change remotes, credentials, or repository settings.
- Delete or overwrite a file not created during the current session, or run `git clean`.

Never rewrite a commit that has already been pushed. Explain the consequences and propose a corrective commit instead. Prefer additive recovery to rewriting or discarding work.
