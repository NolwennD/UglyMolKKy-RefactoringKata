{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = [
    pkgs.sbt
    pkgs.scala
    pkgs.git
    pkgs.openjdk
  ];
}
