FROM mcr.microsoft.com/devcontainers/base:debian AS ssh

ENV DEBIAN_FRONTEND=noninteractive

# base tools
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y --no-install-recommends openssh-server

# user
RUN useradd -m dev
RUN passwd -d dev
RUN echo "dev ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
RUN mkdir -p /home/dev/workspace
RUN chown -R dev:dev /home/dev

# ssh config
RUN echo "PermitEmptyPasswords yes" >> /etc/ssh/sshd_config
RUN mkdir -p /var/run/sshd

WORKDIR /home/dev/workspace
EXPOSE 22

HEALTHCHECK --interval=10s --timeout=3s --start-period=5s --retries=3 \
  CMD ss -tln | grep -q ':22 ' || exit 1

CMD ["/usr/sbin/sshd", "-D"]

FROM ssh AS cpp
# CLion 2025.2.4
RUN mkdir -p /opt
RUN curl -fSL https://download.jetbrains.com/cpp/CLion-2025.2.4.tar.gz | tar -xz -C /opt
RUN ln -s /opt/clion-* /opt/clion
RUN su - dev -c "/opt/clion/bin/remote-dev-server.sh registerBackendLocationForGateway"

# CLI tools
RUN apt-get install -y --no-install-recommends binutils-gold clang-format clang-tidy
RUN rm -rf /var/lib/apt/lists/*