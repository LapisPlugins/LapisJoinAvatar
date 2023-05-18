# LapisJoinAvatar

## Inspiration

This plugin takes heavy inspiration from Picture Login. Unfortunately, that
plugin hasn't been updated for a while and doesn't support some new Minecraft
features like hex colour codes.

You can still check it out on Spigot at
https://www.spigotmc.org/resources/picture-login.4514/

This plugin aims to be an almost perfect replacement meaning we also support Placeholder API
and have added full RGB colour support for text.

### Features that are the same:

- Colour code support for the text that accompanies avatars
- Hex colour codes for the avatar (this was the last update from PictureLogin)
- Support for a custom URL for fetching avatar image
- Support for variables in text

### New features:

- Full hex colour support for text
- Image cache to reduce calls to image API
- Delay the message to display last every time
- LapisFileWatcher to reload config files (this means instant changes for all players who join after you edit the
  config)
- Continued support for future versions

## Examples

The default config will result in the following messages

The only change made for these examples is that the secondary colour was set to #6464ff

That's right, you can use full RGB hex codes anywhere, and they will be shown to players correctly

### with my normal skin

![image](https://imgur.com/L2EF4DB.png)

### With a colourful skin to show off the full RGB

![image](https://imgur.com/8b2yTB3.png)