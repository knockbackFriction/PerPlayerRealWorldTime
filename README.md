# PerPlayerRealWorldTime
Spigot plugin for servers running an (equirectangular) Earth map, it allows time synchronization with the real world time, on a per player basis. The approach used is by calculating the sun altitude, meaning it’s also friendly for latitudes far from the equator. This does not alter mob spawning behavior. It’s not perfect by any means, but it seems I’m the first one to do this, so it’s better than not have anything at all.

## Limitation
- When solar time reaches noon, the sun will teleport from east to west.

## Showcase
https://github.com/user-attachments/assets/29e260f6-f359-47c7-9fbe-f214c55b18eb
