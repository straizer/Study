FROM mcr.microsoft.com/devcontainers/base:debian AS ssh

ENV DEBIAN_FRONTEND=noninteractive

# base tools
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y --no-install-recommends openssh-server inotify-tools

# user
RUN useradd -m dev
RUN passwd -d dev
RUN echo "dev ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
RUN mkdir -p /home/dev/workspace /home/dev/common
RUN chown -R dev:dev /home/dev

# ssh config
RUN echo "PermitEmptyPasswords yes" >> /etc/ssh/sshd_config
RUN mkdir -p /var/run/sshd

# entrypoint (starts SSH srever and mounts /home/dev/workspace from project+common)
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod 0755 /usr/local/bin/docker-entrypoint.sh
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

WORKDIR /home/dev/workspace
EXPOSE 22

HEALTHCHECK --interval=10s --timeout=3s --start-period=5s --retries=3 \
  CMD ss -tln | grep -q ':22 ' || exit 1

CMD ["sleep", "infinity"]


FROM ssh AS c

# CLion 2025.3.1
RUN mkdir -p /opt
RUN curl -fSL https://download.jetbrains.com/cpp/CLion-2025.3.1.tar.gz | tar -xz -C /opt
RUN ln -s /opt/clion-* /opt/clion
RUN su - dev -c "/opt/clion/bin/remote-dev-server.sh registerBackendLocationForGateway"

# CLI tools
RUN apt-get install -y --no-install-recommends binutils-gold clang-format clang-tidy cppcheck libexplain-dev
RUN rm -rf /var/lib/apt/lists/*

# Config files
RUN mkdir -p /usr/share/cppcheck/cfg /usr/share/cppcheck/misra
RUN curl -fsSL https://raw.githubusercontent.com/danmar/cppcheck/main/cfg/posix.cfg \
  -o /usr/share/cppcheck/cfg/posix.cfg
RUN curl -fsSL https://gitlab.com/MISRA/MISRA-C/MISRA-C-2012/tools/-/raw/main/misra_c_2023__headlines_for_cppcheck.txt \
  -o /usr/share/cppcheck/misra/rules.txt