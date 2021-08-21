# IW Project Support

A [Giter8][g8] template for Iterative Works Scala projects!

The project contains default template for Scala projects, referencing plugins that help to maintain IW projects.

## Vision

The goals are:

- to simplify new project creation with favourite tools
- to ease the maintenance of running projects, making it possible to upgrade the stack easily and uniformly accross all the projects

The envisioned process:

1. Create project 1 using `g8 iterative-works/iw-project-support`.
2. My favourite tools are readily available, without a need to specify versions and orgs/names.
3. Develop, launch
4. Update the dependencies to current ones, publish new version of the plugin.
7. Update the plugin and get current tools
8. Fix the code for the new libs and publish updates

This way the overhead of maintaining multiple projects should be lower, than having to do all the version updates manually.

Also, scalafix rewrites could be part of the project to ease the migrations even further.

## Currently included

- IWPluginPresets plugin that pulls in the default plugins used in all projects and shortcuts for a few other commonly used plugins (scalajs, lagom, play etc.)
- IWMaterialsBOM plugin that adds shortcuts to currently used versions and settings of libraries used in IW projects (ZIO, Laminar etc.)
- IWScalaProject auto triggered plugin which provides default settings for all projects and exports currently used scala versions

Template license
----------------
Written in 2021 by Michal Příhoda <michal.prihoda@iterative.works>

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
