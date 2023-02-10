# Rijksmuseum

## Detekt

This project contains [Detekt](https://github.com/detekt/detekt) for static code analysis.  
We ensure detekt usage by adding a git pre-push commit hook.  
To enable this execute the following from the project root folder.

```
$ bash ./scripts/git_hooks.sh
```

If you want to run it manually, use the gradle command

```
$ ./gradlew detekt
```

## Secrets

In order to connect to the Rijksmuseum API, you will need to define `RIJKSMUSEUM_API_KEY` to either your global `gradle.properties` located at: `~/.gradle/gradle.properties` or as a System
environment variable (usually you export them in your `.zshrc` file) with your API key value.

## Todo

There are still some nice to haves in this project like:
- [ ] Implement a caching layer (either in memory or persistent), to allow the usage of the app while offline/avoid redoing calls.
- [ ] Right now, if we end up reaching the end of the pagination, and we become aware of that fact, we dont store it anywhere, so there is a chance we might be repeating requests when not needed(since there is no more items).
- [ ] Some UI love ❤️.